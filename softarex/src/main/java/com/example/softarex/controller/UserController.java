package com.example.softarex.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.DtoConverter;
import com.example.softarex.dto.UserDto;
import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.EmailAlreadyInUse;
import com.example.softarex.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping()
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) throws EmailAlreadyInUse {
        User user = DtoConverter.convertUserDtoToUser(userDto);
        userService.updateUser(user);
        user = userService.getByEmail(userDto.getEmail());
        userDto = DtoConverter.convertUserToDto(user);
        return userDto;
    }
}
