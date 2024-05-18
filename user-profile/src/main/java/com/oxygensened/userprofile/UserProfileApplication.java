package com.oxygensened.userprofile;

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration;
import com.oxygensend.commons_jdk.feign.CommonFeignConfiguration;
import com.oxygensend.commons_jdk.request_context.RequestContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import( {CommonFeignConfiguration.class, ExceptionConfiguration.class, RequestContextConfiguration.class})
@SpringBootApplication
public class UserProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }

}
