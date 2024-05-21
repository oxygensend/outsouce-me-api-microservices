package com.oxygensend.messenger;

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration;
import com.oxygensend.commons_jdk.request_context.RequestContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({ExceptionConfiguration.class, RequestContextConfiguration.class})
@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}

}
