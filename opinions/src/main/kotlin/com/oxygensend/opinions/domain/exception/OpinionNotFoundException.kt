package com.oxygensend.opinions.domain.exception

import com.oxygensend.commonspring.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class OpinionNotFoundException : ApiException("Opinion not found") {
}