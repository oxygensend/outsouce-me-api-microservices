package com.oxygensend.joboffer;

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration;
import com.oxygensend.commons_jdk.feign.CommonFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Import( {ExceptionConfiguration.class, CommonFeignConfiguration.class})
@SpringBootApplication
public class JobOfferApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferApplication.class, args);
    }

}
