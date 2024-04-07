package com.oxygensend.opinions.domain.exception

import com.oxygensend.commons_jdk.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class OpinionLikeException(message: String) : ApiException(message) {

    companion object {
        fun opinionAlreadyLiked(): OpinionLikeException {
            return OpinionLikeException("Opinion already liked")
        }

        fun opinionNotLiked(): OpinionLikeException {
            return OpinionLikeException("Opinion not liked")
        }
    }
}