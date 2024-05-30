package com.oxygensend.joboffer;

import com.oxygensend.commonspring.exception.ExceptionConfiguration;
import com.oxygensend.commonspring.feign.CommonFeignConfiguration;
import com.oxygensend.commonspring.request_context.RequestContextConfiguration;
import com.oxygensend.commonspring.storage.StorageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@Import( {ExceptionConfiguration.class, CommonFeignConfiguration.class, RequestContextConfiguration.class, StorageConfiguration.class})
@SpringBootApplication
public class JobOfferApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferApplication.class, args);
    }


}
