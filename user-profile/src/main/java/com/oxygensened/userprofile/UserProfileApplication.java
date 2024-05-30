package com.oxygensened.userprofile;

import com.oxygensend.commonspring.exception.ExceptionConfiguration;
import com.oxygensend.commonspring.feign.CommonFeignConfiguration;
import com.oxygensend.commonspring.request_context.RequestContextConfiguration;
import com.oxygensend.commonspring.storage.StorageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import( {CommonFeignConfiguration.class, ExceptionConfiguration.class, RequestContextConfiguration.class, StorageConfiguration.class})
@SpringBootApplication
public class UserProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }

}
