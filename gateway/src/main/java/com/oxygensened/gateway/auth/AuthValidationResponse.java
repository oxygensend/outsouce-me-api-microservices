package com.oxygensened.gateway.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public record AuthValidationResponse(
        String userId,
        @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
        List<GrantedAuthority> authorities
){

}
