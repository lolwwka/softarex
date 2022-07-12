package com.example.softarex.service.user;

import java.util.Collections;

import javax.mail.MessagingException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.softarex.entity.Role;
import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.EmailAlreadyInUse;
import com.example.softarex.repository.UserRepository;
import com.example.softarex.service.mail.MailService;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @Override
    public boolean saveUser(User user) throws EmailAlreadyInUse {
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new EmailAlreadyInUse(String.format("Email: %s , already using by another user",user.getEmail()));
        }
        user.setRoles(Collections.singleton(new Role(0)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        try {
            mailService.sendRegistrationMail(user.getEmail());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void updateUser(User user) throws EmailAlreadyInUse {
        if(userRepository.findByEmail(user.getEmail()).getId() != user.getId()){
            throw new EmailAlreadyInUse(String.format("Email: %s , already using by another user",user.getEmail()));
        }
        userRepository.setUserInfoById(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(),
            user.getId());
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
