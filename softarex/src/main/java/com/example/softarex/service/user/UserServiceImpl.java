package com.example.softarex.service.user;

import java.util.Collections;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.softarex.entity.Role;
import com.example.softarex.entity.User;
import com.example.softarex.enums.mail.MailMessageTemplates;
import com.example.softarex.exception.custom.EmailInUseException;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.exception.custom.IncorrectMailException;
import com.example.softarex.exception.custom.IncorrectUserPassException;
import com.example.softarex.repository.UserRepository;
import com.example.softarex.service.mail.MailService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @Override
    public void saveUser(User user) throws RuntimeException, MessagingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailInUseException(String.format("Email: %s , already using by another user", user.getEmail()));
        }
        user.setRoles(Collections.singleton(new Role(0)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        mailService.sendMail(user.getEmail(), MailMessageTemplates.REGISTER.getMailMessage(), MailMessageTemplates.REGISTER.getMailSubject());
    }

    @Override
    public void updateUser(User user) throws RuntimeException {
        if (getByEmail(user.getEmail()).getId() != user.getId()) {
            throw new EmailInUseException(String.format("Email: %s , already using by another user", user.getEmail()));
        }
        userRepository.findById(user.getId()).ifPresentOrElse(user1 -> userRepository.setUserInfoById(user.getEmail(), user.getFirstName()
            , user.getLastName(), user.getPhoneNumber(), user.getId()), () -> {
            throw new IncorrectIdException("User with that id don't exists");
        });
    }

    @Override

    public void changePass(long userId, String currentPass, String newPass) throws RuntimeException, MessagingException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IncorrectIdException("Server problems"));
        if (!bCryptPasswordEncoder.matches(currentPass, user.getPassword())) {
            throw new IncorrectUserPassException("Incorrect current password ");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(user);
        mailService.sendMail(user.getEmail(), MailMessageTemplates.PASS_CHANGE.getMailMessage(), MailMessageTemplates.PASS_CHANGE.getMailSubject());
    }

    @Override
    public void recoverPass(String email) throws RuntimeException, MessagingException {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        String newPass = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        User user = getByEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        String userMail = new StringBuffer().append(MailMessageTemplates.PASS_RECOVERY.getMailMessage()).append("<p>").append(newPass).append("</p>").toString();
        userRepository.save(user);
        mailService.sendMail(email, userMail, MailMessageTemplates.PASS_RECOVERY.getMailSubject());
    }

    @Override
    public User getByEmail(String email) throws RuntimeException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IncorrectMailException("There no user with that email"));
    }
}
