package com.example.softarex.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.softarex.properties.MailProperties;

@Service
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    public MailServiceImpl(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public boolean sendRegistrationMail(String userMail) throws MessagingException {
        String htmlMessage = "<h3>You have been successfully registered at softarex</h3> ";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessage.setContent(htmlMessage, "text/html");
        helper.setFrom("<" + mailProperties.getLogin() + ">");
        helper.setTo(userMail + "<" + userMail + ">");
        helper.setSubject("Registration notification");
        this.mailSender.send(mimeMessage);
        return true;
    }
}
