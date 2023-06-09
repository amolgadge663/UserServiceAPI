package com.amolsoftwares.userservice.UserService.services.impl;

import com.amolsoftwares.userservice.UserService.entities.User;
import com.amolsoftwares.userservice.UserService.exceptions.ResourceNotFoundException;
import com.amolsoftwares.userservice.UserService.repositories.UserRepository;
import com.amolsoftwares.userservice.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow( () -> new ResourceNotFoundException("User not found on server!!! : ID " + userId));
    }
}
