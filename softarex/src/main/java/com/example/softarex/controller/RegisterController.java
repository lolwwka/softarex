package com.example.softarex.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.UserDtoConverter;
import com.example.softarex.dto.UserDto;
import com.example.softarex.entity.User;
import com.example.softarex.service.user.UserService;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public Boolean registerUser(@Valid @RequestBody UserDto userDto) throws RuntimeException, MessagingException {
        User user = UserDtoConverter.convertUserDtoToUser(userDto);
        userService.saveUser(user);
        return Boolean.TRUE;
    }
}
