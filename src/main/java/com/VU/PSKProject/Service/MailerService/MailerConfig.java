package com.VU.PSKProject.Service.MailerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Configuration
public class MailerConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("mail.server"));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("mail.port"))));

        mailSender.setUsername(env.getProperty("mail.sender"));
        mailSender.setPassword(env.getProperty("mail.pwd"));

        Properties props = mailSender.getJavaMailProperties();
        // Don't think these should be parametrized:
        props.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
        props.put("mail.smtp.auth", env.getProperty("mail.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("mail.starttls.enable"));
        props.put("mail.debug", env.getProperty("mail.debug.enable"));
        props.put("mail.smtp.ssl.trust", env.getProperty("mail.ssl.trust"));

        return mailSender;
    }
}
