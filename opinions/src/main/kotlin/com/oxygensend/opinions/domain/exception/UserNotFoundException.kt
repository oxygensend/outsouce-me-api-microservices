package com.oxygensend.opinions.domain.exception

import com.oxygensend.commons_jdk.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException : ApiException("User not found") {
}