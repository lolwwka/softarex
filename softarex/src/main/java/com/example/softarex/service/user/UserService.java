package com.example.softarex.service.user;

import javax.mail.MessagingException;


import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.EmailInUseException;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.exception.custom.IncorrectMailException;
import com.example.softarex.exception.custom.IncorrectUserPassException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    boolean saveUser(User user) throws EmailInUseException, MessagingException;

    User getByEmail(String email) throws IncorrectMailException;

    void updateUser(User user) throws EmailInUseException, IncorrectMailException;

    void changePass(long userId, String currentPass, String newPass) throws IncorrectIdException, IncorrectUserPassException, MessagingException;

    void recoverPass(String email) throws IncorrectMailException, MessagingException;
}
