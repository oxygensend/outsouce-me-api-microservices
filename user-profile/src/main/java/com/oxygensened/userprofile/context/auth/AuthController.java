package com.oxygensened.userprofile.context.auth;

import com.oxygensened.userprofile.context.auth.dto.EmailRequest;
import com.oxygensened.userprofile.context.auth.dto.RegisterRequest;
import com.oxygensened.userprofile.context.auth.dto.RegisterView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    RegisterView register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @Operation(summary = "Resend email verification link")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/resend_email_verification_link")
    void resendEmailVerificationLink(@Valid @RequestBody EmailRequest request) {
        authService.resendEmailVerificationLink(request.email());
    }

    @Operation(summary = "Resend password reset link")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/resend_password_reset_link")
    void resendPasswordResetLink(@Valid @RequestBody EmailRequest request) {
        authService.resendPasswordResetLink(request.email());
    }
}
