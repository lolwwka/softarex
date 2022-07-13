package com.example.softarex.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.DtoConverter;
import com.example.softarex.dto.NewPassDto;
import com.example.softarex.dto.UserDto;
import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.EmailInUseException;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.exception.custom.IncorrectMailException;
import com.example.softarex.exception.custom.IncorrectUserPassException;
import com.example.softarex.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/{id}/change")
    public UserDto updateUser(@PathVariable long id, @Valid @RequestBody UserDto userDto) throws EmailInUseException, IncorrectMailException {
        userDto.setId(id);
        User user = DtoConverter.convertUserDtoToUser(userDto);
        userService.updateUser(user);
        user = userService.getByEmail(userDto.getEmail());
        userDto = DtoConverter.convertUserToDto(user);
        return userDto;
    }

    @PutMapping(value = "/{id}/changePass")
    public void updateUserPass(@PathVariable long id, @Valid @RequestBody NewPassDto newPassDto) throws IncorrectIdException, IncorrectUserPassException, MessagingException {
        userService.changePass(id, newPassDto.getCurrentPass(), newPassDto.getNewPass());
    }

    @PostMapping(value = "/passRecovery/{email}")
    public Boolean recoverPass(@PathVariable String email) throws IncorrectMailException, MessagingException {
        userService.recoverPass(email);
        return Boolean.TRUE;
    }
}
