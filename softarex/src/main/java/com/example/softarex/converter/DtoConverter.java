package com.example.softarex.converter;

import com.example.softarex.dto.UserDto;
import com.example.softarex.entity.User;

public class DtoConverter {
    public static User convertUserDtoToUser(UserDto userDto){
        User user = new User();
        if(userDto.getId() != 0){
            user.setId(userDto.getId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserDto convertUserToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        if(user.getFirstName() != null) {
            userDto.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null) {
            userDto.setLastName(user.getLastName());
        }
        if(user.getPhoneNumber() != null) {
            userDto.setPhoneNumber(user.getPhoneNumber());
        }
        return userDto;
    }
}
