package com.amolsoftwares.userservice.UserService.services.impl;

import com.amolsoftwares.userservice.UserService.entities.Rating;
import com.amolsoftwares.userservice.UserService.entities.User;
import com.amolsoftwares.userservice.UserService.exceptions.ResourceNotFoundException;
import com.amolsoftwares.userservice.UserService.repositories.UserRepository;
import com.amolsoftwares.userservice.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

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
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User not found on server!!! : ID " + userId));
        //rest call
        ArrayList<Rating> userRating = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), ArrayList.class);
        user.setRatings(userRating);
        return user;
    }
}
