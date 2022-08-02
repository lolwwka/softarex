package com.example.softarex.converter;

import com.example.softarex.dto.UserCredentialsDto;
import com.example.softarex.entity.User;

public class UserCredentialsDtoConverter {

    public static User convertUserCredToDto(UserCredentialsDto userCredentialsDto) {
        User user = new User();
        user.setPassword(userCredentialsDto.getPassword());
        user.setEmail(userCredentialsDto.getEmail());
        if (userCredentialsDto.getFirstName() != null) {
            user.setFirstName(userCredentialsDto.getFirstName());
        }
        if (userCredentialsDto.getLastName() != null) {
            user.setLastName(userCredentialsDto.getLastName());
        }
        if (userCredentialsDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userCredentialsDto.getPhoneNumber());
        }
        return user;
    }

}
