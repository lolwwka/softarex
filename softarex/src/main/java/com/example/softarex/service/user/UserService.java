package com.example.softarex.service.user;

import javax.transaction.Transactional;


import com.example.softarex.entity.User;
import com.example.softarex.exception.custom.EmailAlreadyInUse;

@Transactional
public interface UserService{
    boolean saveUser(User user) throws EmailAlreadyInUse;
    User getByEmail(String email);
    void updateUser(User user) throws EmailAlreadyInUse;
}
