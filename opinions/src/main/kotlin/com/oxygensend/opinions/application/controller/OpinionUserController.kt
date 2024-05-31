package com.oxygensend.opinions.application.controller

import com.oxygensend.opinions.application.request.CreateUserRequest
import com.oxygensend.opinions.application.service.OpinionUserService
import com.oxygensend.opinions.application.view.UserOpinionsDetailsView
import com.oxygensend.opinions.application.view.UserView
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class OpinionUserController(private val userService: OpinionUserService) {

    @GetMapping("/{userId}/opinions-details")
    fun getUserOpinionsDetails(@PathVariable userId: String): UserOpinionsDetailsView = userService.getUserOpinionsDetails(userId)

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): UserView = userService.createUser(request.toCommand())
}