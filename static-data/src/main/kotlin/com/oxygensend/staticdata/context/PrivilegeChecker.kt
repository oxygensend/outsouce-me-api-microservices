package com.oxygensend.staticdata.context

import com.oxygensend.commonspring.exception.AccessDeniedException
import com.oxygensend.commonspring.exception.UnauthorizedException
import com.oxygensend.commonspring.request_context.RequestContext
import org.springframework.stereotype.Component

@Component
class PrivilegeChecker(private val requestContext: RequestContext) {

    fun checkAdminPrivileges() {
        if (requestContext.isAuthorized.not()) throw UnauthorizedException()
        if (requestContext.hasAuthority("ROLE_ADMIN").not()) throw AccessDeniedException()
    }

    fun checkEditorPrivileges() {
        if (requestContext.isAuthorized.not()) throw UnauthorizedException()
        if (requestContext.hasAuthority("ROLE_EDITOR").not() && requestContext.hasAuthority("ROLE_ADMIN").not()) throw AccessDeniedException()
    }

}