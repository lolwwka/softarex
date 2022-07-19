package com.example.softarex.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.UserDtoConverter;
import com.example.softarex.dto.NewPassDto;
import com.example.softarex.dto.UserDto;
import com.example.softarex.entity.User;
import com.example.softarex.routs.UserControllerRouts;
import com.example.softarex.service.user.UserService;

@RestController
@RequestMapping(UserControllerRouts.MAIN_ROUT)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/{id}")
    public UserDto updateUser(@PathVariable long id, @Valid @RequestBody UserDto userDto) throws RuntimeException {
        userDto.setId(id);
        User user = UserDtoConverter.convertUserDtoToUser(userDto);
        userService.updateUser(user);
        user = userService.getByEmail(userDto.getEmail());
        userDto = UserDtoConverter.convertUserToDto(user);
        return userDto;
    }

    @PutMapping(value = "/{id}/" + UserControllerRouts.PASS_CHANGE)
    public void updateUserPass(@PathVariable long id, @Valid @RequestBody NewPassDto newPassDto) throws RuntimeException, MessagingException {
        userService.changePass(id, newPassDto.getCurrentPass(), newPassDto.getNewPass());
    }

    @PostMapping(value = "/" + UserControllerRouts.PASS_RECOVERY  + "/{email}")
    public Boolean recoverPass(@PathVariable String email) throws RuntimeException, MessagingException {
        userService.recoverPass(email);
        return Boolean.TRUE;
    }
}
