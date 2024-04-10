package com.oxygensend.opinions.context

import com.oxygensend.opinions.context.request.CreateUserRequest
import com.oxygensend.opinions.context.view.UserOpinionsDetailsView
import com.oxygensend.opinions.context.view.UserView
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class OpinionUserController(private val userService: OpinionUserService) {

    @GetMapping("/{userId}/opinions-details")
    fun getUserOpinionsDetails(@PathVariable userId: String): UserOpinionsDetailsView = userService.getUserOpinionsDetails(userId)

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): UserView = userService.createUser(request.toCommand())
}