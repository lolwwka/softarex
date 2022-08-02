package com.example.softarex.service.user;

import javax.mail.MessagingException;

import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.EmailInUseException;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.exception.custom.IncorrectMailException;
import com.example.softarex.exception.custom.IncorrectUserPassException;

@Transactional
public interface UserService {

    void saveUser(User user) throws EmailInUseException, MessagingException;

    User getByEmail(String email) throws IncorrectMailException;

    void updateUser(User user) throws EmailInUseException, IncorrectMailException;

    void changePass(long userId, String currentPass, String newPass) throws IncorrectIdException, IncorrectUserPassException, MessagingException;

    void recoverPass(String email) throws IncorrectMailException, MessagingException;
}
