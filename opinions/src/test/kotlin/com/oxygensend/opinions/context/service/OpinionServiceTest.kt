package com.oxygensend.opinions.context.service

import com.oxygensend.opinions.context.command.AddCommentCommand
import com.oxygensend.opinions.context.command.CreateOpinionCommand
import com.oxygensend.opinions.context.view.CommentView
import com.oxygensend.opinions.context.view.OpinionView
import com.oxygensend.opinions.context.view.UserView
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.OpinionRepository
import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.opinions.domain.aggregate.AggregatedOpinionDto
import com.oxygensend.opinions.domain.aggregate.AuthorDto
import com.oxygensend.opinions.domain.aggregate.CommentDto
import com.oxygensend.opinions.domain.aggregate.OpinionAggregateRepository
import com.oxygensend.opinions.domain.aggregate.filter.CommentSort
import com.oxygensend.opinions.domain.aggregate.filter.CommentsFilter
import com.oxygensend.opinions.domain.aggregate.filter.OpinionsFilter
import com.oxygensend.opinions.domain.exception.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@ExtendWith(MockitoExtension::class)
internal class OpinionServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var opinionRepository: OpinionRepository

    @Mock
    private lateinit var opinionAggregateRepository: OpinionAggregateRepository

    @InjectMocks
    private lateinit var opinionService: OpinionService


    @Test
    fun `delete opinion should delete opinion`() {
        val opinionId = ObjectId()
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)

        opinionService.deleteOpinion(opinionId)

        Mockito.verify(opinionRepository).delete(opinion)
    }

    @Test
    fun `delete opinion should throw exception when opinion not found`() {
        val opinionId = ObjectId()

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(null)

        assertThrows<OpinionNotFoundException> {
            opinionService.deleteOpinion(opinionId)
        }
    }

    @Test
    fun `create opinion should create opinion`() {
        val command = CreateOpinionCommand(
            authorId = "authorId",
            receiverId = "receiverId",
            text = "text",
            scale = 5
        )
        val user = User(
            id = command.authorId,
            fullName = "fullName",
            internal = false
        )

        val opinion = mock(Opinion::class.java)
        Mockito.`when`(userRepository.findById(command.authorId)).thenReturn(user)
        Mockito.`when`(userRepository.findById(command.receiverId)).thenReturn(mock(User::class.java))
        Mockito.`when`(opinionRepository.existsByAuthorAndReceiver(command.authorId, command.receiverId)).thenReturn(false)
        Mockito.`when`(opinionRepository.save(any())).thenReturn(opinion)

        val result = opinionService.createOpinion(command)

        Mockito.verify(opinionRepository).save(any())
        assertThat(result.author.id).isEqualTo(command.authorId)
        assertThat(result.author.fullName).isEqualTo(user.fullName)
        assertThat(result.author.thumbnailPath).isEqualTo(user.thumbnailPath)
        assertThat(result.text).isEqualTo(command.text)
        assertThat(result.scale).isEqualTo(command.scale)
    }

    @Test
    fun `create opinion should throw exception when author not found`() {
        val command = CreateOpinionCommand(
            authorId = "authorId",
            receiverId = "receiverId",
            text = "text",
            scale = 5
        )

        Mockito.`when`(userRepository.findById(command.receiverId)).thenReturn(mock(User::class.java))
        Mockito.`when`(opinionRepository.existsByAuthorAndReceiver(command.authorId, command.receiverId)).thenReturn(false)
        Mockito.`when`(userRepository.findById(command.authorId)).thenReturn(null)

        assertThrows<NoSuchUserException> {
            opinionService.createOpinion(command)
        }
    }

    @Test
    fun `create opinion should throw exception when receiver not found`() {
        val command = CreateOpinionCommand(
            authorId = "authorId",
            receiverId = "receiverId",
            text = "text",
            scale = 5
        )

        Mockito.`when`(userRepository.findById(command.receiverId)).thenReturn(null)

        assertThrows<NoSuchUserException> {
            opinionService.createOpinion(command)
        }
    }

    @Test
    fun `create opinion should throw exception when opinion from this author already exists`() {
        val command = CreateOpinionCommand(
            authorId = "authorId",
            receiverId = "receiverId",
            text = "text",
            scale = 5
        )

        Mockito.`when`(userRepository.findById(command.receiverId)).thenReturn(mock(User::class.java))
        Mockito.`when`(opinionRepository.existsByAuthorAndReceiver(command.authorId, command.receiverId)).thenReturn(true)

        assertThrows<OpinionAlreadyExistException> {
            opinionService.createOpinion(command)
        }
    }

    @Test
    fun `add like should add like`() {
        val opinionId = ObjectId();
        val userId = ObjectId().toHexString();
        val opinion = mock(Opinion::class.java)
        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(userId)).thenReturn(mock(User::class.java))

        opinionService.addLike(opinionId, userId)

        Mockito.verify(opinion).addLike(userId)
        Mockito.verify(opinionRepository).save(opinion)
    }

    @Test
    fun `add like should throw exception when opinion not found`() {
        val opinionId = ObjectId();
        val userId = ObjectId().toHexString();

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(null)

        assertThrows<OpinionNotFoundException> {
            opinionService.addLike(opinionId, userId)
        }
    }

    @Test
    fun `add like should throw exception when user not found`() {
        val opinionId = ObjectId();
        val userId = ObjectId().toHexString();
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(userId)).thenReturn(null)

        assertThrows<NoSuchUserException> {
            opinionService.addLike(opinionId, userId)
        }
    }

    @Test
    fun `add like should throw exception when opinion already liked`() {
        val opinionId = ObjectId();
        val userId = ObjectId().toHexString();
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(userId)).thenReturn(mock(User::class.java))
        Mockito.`when`(opinion.hasLiked(userId)).thenReturn(true)

        assertThrows<OpinionLikeException> {
            opinionService.addLike(opinionId, userId)
        }
    }

    @Test
    fun `add dislike should add dislike`() {
        val opinionId = ObjectId();
        val userId = ObjectId().toHexString();
        val opinion = mock(Opinion::class.java)
        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(userId)).thenReturn(mock(User::class.java))
        Mockito.`when`(opinion.hasLiked(userId)).thenReturn(true)

        opinionService.addDislike(opinionId, userId)

        Mockito.verify(opinion).removeLike(userId)
        Mockito.verify(opinionRepository).save(opinion)
    }

    @Test
    fun `add dislike should throw exception when opinion not found`() {
        val opinionId = ObjectId()
        val userId = ObjectId().toHexString()

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(null)

        assertThrows<OpinionNotFoundException> {
            opinionService.addDislike(opinionId, userId)
        }
    }

    @Test
    fun `add dislike should throw exception when user not found`() {
        val opinionId = ObjectId()
        val userId = ObjectId().toHexString()
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(userId)).thenReturn(null)

        assertThrows<NoSuchUserException> {
            opinionService.addDislike(opinionId, userId)
        }
    }

    @Test
    fun `add dislike should throw exception when opinion not liked`() {
        val opinionId = ObjectId()
        val userId = ObjectId().toHexString()
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(userId)).thenReturn(mock(User::class.java))
        Mockito.`when`(opinion.hasLiked(userId)).thenReturn(false)

        assertThrows<OpinionLikeException> {
            opinionService.addDislike(opinionId, userId)
        }
    }

    @Test
    fun `add comment should add comment`() {
        val opinionId = ObjectId()
        val command = AddCommentCommand(author = ObjectId().toHexString(), text = "text")
        val user = User(id = command.author, fullName = "fullName", internal = false)

        val opinion = mock(Opinion::class.java)
        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(command.author)).thenReturn(user)

        val result = opinionService.addComment(opinionId, command)

        Mockito.verify(opinion).addComment(any())
        Mockito.verify(opinionRepository).save(opinion)
        assertThat(result.author.id).isEqualTo(command.author)
        assertThat(result.author.fullName).isEqualTo(user.fullName)
        assertThat(result.author.thumbnailPath).isEqualTo(user.thumbnailPath)
        assertThat(result.text).isEqualTo(command.text)

    }

    @Test
    fun `add comment should throw exception when opinion not found`() {
        val opinionId = ObjectId()
        val command = AddCommentCommand(
            author = ObjectId().toHexString(),
            text = "text"
        )

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(null)

        assertThrows<OpinionNotFoundException> {
            opinionService.addComment(opinionId, command)
        }
    }

    @Test
    fun `add comment should throw exception when user not found`() {
        val opinionId = ObjectId()
        val command = AddCommentCommand(
            author = ObjectId().toHexString(),
            text = "text"
        )
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(userRepository.findById(command.author)).thenReturn(null)

        assertThrows<NoSuchUserException> {
            opinionService.addComment(opinionId, command)
        }
    }

    @Test
    fun `delete comment should delete comment`() {
        val opinionId = ObjectId()
        val commentId = ObjectId()
        val opinion = mock(Opinion::class.java)
        val comment = mock(Opinion.Comment::class.java)
        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(opinion.findCommentById(commentId)).thenReturn(comment)

        opinionService.deleteComment(opinionId, commentId)

        Mockito.verify(opinion).removeComment(comment)
        Mockito.verify(opinionRepository).save(opinion)
    }

    @Test
    fun `delete comment should throw exception when opinion not found`() {
        val opinionId = ObjectId()
        val commentId = ObjectId()

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(null)

        assertThrows<OpinionNotFoundException> {
            opinionService.deleteComment(opinionId, commentId)
        }
    }

    @Test
    fun `delete comment should throw exception when comment not found`() {
        val opinionId = ObjectId()
        val commentId = ObjectId()
        val opinion = mock(Opinion::class.java)

        Mockito.`when`(opinionRepository.findById(opinionId)).thenReturn(opinion)
        Mockito.`when`(opinion.findCommentById(commentId)).thenReturn(null)

        assertThrows<NoSuchCommentException> {
            opinionService.deleteComment(opinionId, commentId)
        }
    }

    @Test
    fun `get comments should return comments`() {
        val opinion = mock(Opinion::class.java)
        val filter = CommentsFilter(
            opinionId = ObjectId(),
            sort = CommentSort.NEWEST
        )
        val pageable = Pageable.ofSize(10);
        val comment = CommentDto(
            id = ObjectId(),
            author = AuthorDto(
                id = ObjectId().toHexString(),
                fullName = "fullName",
                thumbnailPath = "thumbnailPath"
            ),
            text = "text"
        )
        val expectedView = CommentView(
            id = comment.id.toHexString(),
            author = UserView(
                id = comment.author.id,
                fullName = comment.author.fullName,
                thumbnailPath = comment.author.thumbnailPath
            ),
            text = comment.text,
            createdAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(comment.id.timestamp.toLong()), ZoneId.systemDefault())

        )
        Mockito.`when`(opinionRepository.findById(filter.opinionId)).thenReturn(opinion)
        Mockito.`when`(opinionAggregateRepository.getOpinionComments(filter, pageable)).thenReturn(listOf(comment))

        val result = opinionService.getComments(filter, pageable)

        assertThat(result).isNotNull
        assertThat(result.data.size).isEqualTo(1)
        assertThat(result.data[0]).isEqualTo(expectedView)
    }

    @Test
    fun `get comments should throw exception when opinion not found`() {
        val filter = CommentsFilter(
            opinionId = ObjectId(),
            sort = CommentSort.NEWEST
        )
        val pageable = Pageable.ofSize(10)

        Mockito.`when`(opinionRepository.findById(filter.opinionId)).thenReturn(null)

        assertThrows<OpinionNotFoundException> {
            opinionService.getComments(filter, pageable)
        }
    }

    @Test
    fun `get opinions should return opinions`() {
        val filter = mock(OpinionsFilter::class.java)
        val pageable = Pageable.ofSize(10)
        val opinion = AggregatedOpinionDto(
            id = ObjectId(),
            author = AuthorDto(
                id = ObjectId().toHexString(),
                fullName = "fullName",
                thumbnailPath = "thumbnailPath"
            ),
            receiver = ObjectId().toHexString(),
            text = "text",
            scale = 5,
            likes = 0,
            liked = false,
            numberOfComments = 0
        )
        val expectedView = OpinionView(
            id = opinion.id.toHexString(),
            author = UserView(
                id = opinion.author.id,
                fullName = opinion.author.fullName,
                thumbnailPath = opinion.author.thumbnailPath
            ),
            text = opinion.text,
            scale = opinion.scale,
            likes = 0,
            liked = false,
            numberOfComments = 0
        )
        Mockito.`when`(opinionAggregateRepository.findAggregatedOpinions(filter, pageable)).thenReturn(PageImpl(listOf(opinion)))

        val result = opinionService.getOpinions(filter, pageable)

        assertThat(result).isNotNull
        assertThat(result.data.size).isEqualTo(1)
        assertThat(result.data[0]).isEqualTo(expectedView)
    }

}