package com.example.softarex.enums.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum MailMessageTemplates {
    REGISTER("Registration notification",
        "<h3>You have been successfully registered at softarex</h3>"),
    PASS_CHANGE("Password changed",
        "<h3>You have been changed password at softarex.com</h3>"),
    PASS_RECOVERY("Password recovery",
        "<h3>You have been ask for recovery pass. There it is :</h3>")
    ;
    @Setter
    private String mailSubject;
    @Setter
    private String mailMessage;

}
