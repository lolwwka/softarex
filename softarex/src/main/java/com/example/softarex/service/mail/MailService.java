package com.example.softarex.service.mail;

import javax.mail.MessagingException;

public interface MailService {

    boolean sendMail(String userMail, String message, String subject) throws MessagingException;
}
