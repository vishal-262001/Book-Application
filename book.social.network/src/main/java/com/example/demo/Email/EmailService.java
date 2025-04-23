package com.example.demo.Email;

import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
@Configuration
public class EmailService {



    private final  JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Async
    public void sendEmail( String to,
                           String userName,
                           EmailTemplateName emailTemplateName,
                           String confirmationUrl,
                           String activationCode,
                           String subject
    ) throws MessagingException {

        String templateName = emailTemplateName != null ? emailTemplateName.name() : "confirm-email";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());

        Map<String, Object> properties = new HashMap<>();
        properties.put("userName", userName);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("vishalhadke262@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = springTemplateEngine.process(templateName, context);
        helper.setText(template, true);

        javaMailSender.send(mimeMessage);
        System.out.println("Mail Send Successfully;");
    }



}
