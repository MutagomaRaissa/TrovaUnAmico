package com.trovaunamico.TrovaUnAmico.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${spring.sendgrid.sender-email}")
    private String senderEmail;



    @Async
    public void sendEmail(String to, String subject, String body) {
        Email from = new Email(senderEmail);
      Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.info("Email sent to {} | Status Code: {}", to, response.getStatusCode());
            log.debug("Response Body: {}", response.getBody());
            log.debug("Response Headers: {}", response.getHeaders());

        } catch (Exception e) {
            log.error("Failed to send email to {}", to, e);
        }
    }
}