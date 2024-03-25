package com.oxygensened.userprofile.infrastructure.services.auth;

import com.oxygensened.userprofile.infrastructure.services.auth.dto.AuthRegisterRequest;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.AuthResponse;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.TokenResponse;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.UserIdRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

interface AuthClient {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/auth/register")
    AuthResponse register(@RequestBody AuthRegisterRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/v1/users/generate_email_verification_token")
    TokenResponse generateEmailVerificationToken(@RequestBody UserIdRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/v1/users/generate_password_reset_token")
    TokenResponse generatePasswordResetToken(@RequestBody UserIdRequest request);
}
