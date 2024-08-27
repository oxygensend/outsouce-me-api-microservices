package com.oxygensened.userprofile.application;

import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensend.commonspring.exception.UnauthorizedException;
import com.oxygensend.commonspring.request_context.RequestContext;
import org.springframework.stereotype.Component;

@Component
class PrivilegeVerifier {
    private final RequestContext requestContext;

    PrivilegeVerifier(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public void checkAdminPrivileges() {
        if (!requestContext.isAuthorized()) {
            throw new UnauthorizedException();
        }
        if (!requestContext.hasAuthority("ROLE_ADMIN")) {
            throw new AccessDeniedException();
        }
    }

    public void checkEditorPrivileges() {
        if (!requestContext.isAuthorized()) {
            throw new UnauthorizedException();
        }
        if (!requestContext.hasAuthority("ROLE_ADMIN") && !requestContext.hasAuthority("ROLE_EDITOR")) {
            throw new AccessDeniedException();
        }
    }
}
