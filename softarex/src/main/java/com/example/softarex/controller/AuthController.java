package com.example.softarex.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.DtoConverter;
import com.example.softarex.dto.UserDto;
import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.IncorrectMailException;
import com.example.softarex.service.user.UserService;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto authenticate(Principal principal) throws IncorrectMailException {
        User user = userService.getByEmail(principal.getName());
        UserDto userDto = DtoConverter.convertUserToDto(user);
        return userDto;
    }
}
