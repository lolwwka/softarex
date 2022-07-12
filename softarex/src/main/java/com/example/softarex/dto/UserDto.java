package com.example.softarex.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.example.softarex.entity.Role;

@Validated
public class UserDto {
    private long id;
    @Email(message = "Invalid email input")
    private String email;
    @NotBlank(message = "Password can't be empty")
    @Size(max = 25, message = "Password must be less 25 symbols")
    @Size(min = 5, message = "Password must be more 5 symbols")
    private String password;
    @Pattern(regexp="^$|^[A-Za-z]*$",message = "First name must include only letters")
    @Size(max = 25, message = "First name must be less 25 letters")
    private String firstName;
    @Pattern(regexp="^$|^[A-Za-z]*$",message = "Last name must include only letters")
    @Size(max = 25, message = "Last name must be less 25 letters")
    private String lastName;
    @Pattern(regexp = "^$|^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Invalid phone input")
    private String phoneNumber;
    private Set<Role> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
