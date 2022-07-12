package com.example.softarex.service.mail;

import javax.mail.MessagingException;

public interface MailService {
    boolean sendRegistrationMail(String userMail) throws MessagingException;
}
