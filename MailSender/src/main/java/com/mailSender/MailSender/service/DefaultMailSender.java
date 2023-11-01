package com.mailSender.MailSender.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.service.interfaces.SendEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DefaultMailSender implements SendEmail {

    private final JavaMailSender senderMails;

    @Override
    public void sendMail(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(message.getEmailTo());
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setText(message.getText());
        senderMails.send(simpleMailMessage);
        log.info("Письмо на почту {} отправлено",message.getEmailTo());
    }
}
