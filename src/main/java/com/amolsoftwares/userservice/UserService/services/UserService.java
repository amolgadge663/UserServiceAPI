package com.amolsoftwares.userservice.UserService.services;

import com.amolsoftwares.userservice.UserService.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUser(String userId);

    //TODO: deleteUser
    //TODO: updateUser
}
