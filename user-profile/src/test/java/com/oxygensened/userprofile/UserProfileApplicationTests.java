package com.oxygensened.userprofile;

import com.github.javafaker.Faker;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class UserProfileApplicationTests {

    @Test
    void contextLoads() {
        var faker = Faker.instance(Locale.of("pl_Pl"));
        var z = faker.name().lastName();
        var y = faker.name().firstName();

        var s = faker.address().city();
    }

}
