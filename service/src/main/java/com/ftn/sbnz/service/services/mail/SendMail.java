package com.ftn.sbnz.service.services.mail;

import com.ftn.sbnz.model.models.EmailDetails;
import com.ftn.sbnz.service.configProperties.CustomProperties;
import com.ftn.sbnz.service.exceptions.MailFailedToSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMail {
    private final JavaMailSender javaMailSender;

    private final CustomProperties customProperties;

    @Async
    public void execute(EmailDetails details) {
        sendMail(details.getRecipient(), details.getMessage(), details.getSubject());
    }


    private void sendMail(String recipient, String message, String subject) {
        try {
            final SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(customProperties.getSenderEmail());
            mailMessage.setTo(recipient);
            mailMessage.setText(message);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new MailFailedToSendException();
        }
    }
}
