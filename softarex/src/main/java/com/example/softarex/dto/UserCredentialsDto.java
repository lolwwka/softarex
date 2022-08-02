package com.example.softarex.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.example.softarex.constants.validation.Message;
import com.example.softarex.constants.validation.Patterns;
import com.example.softarex.entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class UserCredentialsDto {

    private long id;
    @Email(message = Message.EMAIL)
    private String email;
    @NotBlank(message = Message.PASSWORD_BLANK)
    @Size(max = 25, message = Message.PASSWORD_MAX)
    @Size(min = 5, message = Message.PASSWORD_MIN)
    private String password;
    @Pattern(regexp = Patterns.FIRST_NAME, message = Message.FIRST_NAME)
    @Size(max = 25, message = Message.FIRST_NAME_MAX)
    private String firstName;
    @Pattern(regexp = Patterns.LAST_NAME, message = Message.LAST_NAME)
    @Size(max = 25, message = Message.LAST_NAME_MAX)
    private String lastName;
    @Pattern(regexp = Patterns.PHONE, message = Message.PHONE)
    private String phoneNumber;
    private Set<Role> roles;
}
