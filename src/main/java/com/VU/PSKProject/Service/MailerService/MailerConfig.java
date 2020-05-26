package com.VU.PSKProject.Service.MailerService;

import com.VU.PSKProject.Config.MailingConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailerConfig {
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MailingConfig.MAIL_SERVER);
        mailSender.setPort(MailingConfig.PORT);

        mailSender.setUsername(MailingConfig.SENDER_MAIL);
        mailSender.setPassword(MailingConfig.PWD);

        Properties props = mailSender.getJavaMailProperties();
        // Don't thing these should be parametrized:
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }
}
