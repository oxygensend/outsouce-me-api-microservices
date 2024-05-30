package com.oxygensend.staticdata.domain.exception

import com.oxygensend.commonspring.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class AlreadyLoadingException(message: String) : ApiException(message) {
}