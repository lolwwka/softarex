package com.example.softarex.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.softarex.properties.MailProperties;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    public MailServiceImpl(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public boolean sendMail(String userMail, String message, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessage.setContent(message, "text/html");
        helper.setFrom("<" + mailProperties.getLogin() + ">");
        helper.setTo(userMail + "<" + userMail + ">");
        helper.setSubject("Registration notification");
        this.mailSender.send(mimeMessage);
        return true;
    }
}
