package com.oxygensened.userprofile;

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration;
import com.oxygensend.commons_jdk.feign.CommonFeignConfiguration;
import com.oxygensened.userprofile.config.properties.UserProfileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties( {UserProfileProperties.class})
@Import( {CommonFeignConfiguration.class, ExceptionConfiguration.class})
@SpringBootApplication
public class UserProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }

}
