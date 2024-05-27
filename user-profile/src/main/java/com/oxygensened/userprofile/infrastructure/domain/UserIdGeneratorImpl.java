package com.oxygensened.userprofile.infrastructure.domain;

import com.oxygensened.userprofile.domain.service.UserIdGenerator;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
final class UserIdGeneratorImpl implements UserIdGenerator {
    @Override
    public long generate() {
        var secureRandom = new SecureRandom(new byte[] {100});
        return Math.abs(secureRandom.nextLong());
    }
}
