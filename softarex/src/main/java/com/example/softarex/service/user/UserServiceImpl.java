package com.example.softarex.service.user;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.softarex.entity.Role;
import com.example.softarex.entity.User;
import com.example.softarex.enums.mail.Mail;
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
    public boolean saveUser(User user) throws EmailInUseException, MessagingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailInUseException(String.format("Email: %s , already using by another user", user.getEmail()));
        }
        user.setRoles(Collections.singleton(new Role(0)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        mailService.sendMail(user.getEmail(), Mail.REGISTER.getMailMessage(), Mail.REGISTER.getMailSubject());
        return true;
    }

    @Override
    public void updateUser(User user) throws EmailInUseException, IncorrectMailException {
        if (getByEmail(user.getEmail()).getId() != user.getId()) {
            throw new EmailInUseException(String.format("Email: %s , already using by another user", user.getEmail()));
        }
        userRepository.setUserInfoById(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(),
            user.getId());
    }

    @Override

    public void changePass(long userId, String currentPass, String newPass) throws IncorrectIdException, IncorrectUserPassException, MessagingException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IncorrectIdException("Server problems"));
        if(!bCryptPasswordEncoder.matches(currentPass,user.getPassword())){
            throw new IncorrectUserPassException("Incorrect current password ");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(user);
        mailService.sendMail(user.getEmail(), Mail.PASS_CHANGE.getMailMessage(), Mail.PASS_CHANGE.getMailSubject());
    }

    @Override
    public void recoverPass(String email) throws IncorrectMailException, MessagingException {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        String newPass = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        User user = getByEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        String userMail = new StringBuffer()
            .append(Mail.PASS_RECOVERY.getMailMessage())
            .append("<p>")
            .append(newPass)
            .append("</p>").toString();
        userRepository.save(user);
        mailService.sendMail(email, userMail, Mail.PASS_RECOVERY.getMailSubject());
    }

    @Override
    public User getByEmail(String email) throws IncorrectMailException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IncorrectMailException("There no user with that email"));
    }
}
