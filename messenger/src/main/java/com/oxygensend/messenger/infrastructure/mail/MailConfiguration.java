package com.oxygensend.messenger.infrastructure.mail;

import jakarta.mail.MessagingException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfiguration {

    @Bean
    MailServiceImpl mailService(JavaMailSender javaMailSender) {
        return new MailServiceImpl(javaMailSender);
    }

    @Bean
    JavaMailSender javaMailSender(MailProperties mailProperties) throws MessagingException {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.host());
        mailSender.setPort(mailProperties.port());
        mailSender.setUsername(mailProperties.username());
        mailSender.setPassword(mailProperties.password());
        mailSender.setProtocol(mailProperties.protocol());
        var properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", mailProperties.auth());
        properties.put("mail.smtp.starttls.enable", mailProperties.starttlsEnable());
        properties.put("mail.debug", mailProperties.debug());
        if (mailProperties.testConnection()) {
            mailSender.testConnection();
        }
        return mailSender;
    }
}
