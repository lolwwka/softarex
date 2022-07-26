package com.example.softarex.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.UserCredentialsDtoConverter;
import com.example.softarex.dto.UserCredentialsDto;
import com.example.softarex.entity.User;
import com.example.softarex.constants.routs.RegisterControllerRouts;
import com.example.softarex.service.user.UserService;

@RestController
@RequestMapping(RegisterControllerRouts.MAIN_ROUT)
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public void registerUser(@Valid @RequestBody UserCredentialsDto userCredentialsDto) throws RuntimeException, MessagingException {
        User user = UserCredentialsDtoConverter.convertUserCredToDto(userCredentialsDto);
        userService.saveUser(user);
    }
}
