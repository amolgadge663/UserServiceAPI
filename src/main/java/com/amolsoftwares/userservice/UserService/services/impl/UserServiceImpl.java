package com.amolsoftwares.userservice.UserService.services.impl;

import com.amolsoftwares.userservice.UserService.client.HotelsClient;
import com.amolsoftwares.userservice.UserService.client.RatingsClient;
import com.amolsoftwares.userservice.UserService.entities.Hotel;
import com.amolsoftwares.userservice.UserService.entities.Rating;
import com.amolsoftwares.userservice.UserService.entities.User;
import com.amolsoftwares.userservice.UserService.exceptions.ResourceNotFoundException;
import com.amolsoftwares.userservice.UserService.repositories.UserRepository;
import com.amolsoftwares.userservice.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RatingsClient ratingsClient;

    @Autowired
    private HotelsClient hotelsClient;

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        Rating rate = new Rating();

        List<User> userList = userRepository.findAll().stream().map(user -> {
            //call the rating from user id
            List<Rating> ratingByUserId = ratingsClient.getRatingByUserId(user.getUserId());
            List<String> hotelIds = ratingByUserId.stream().map(Rating::getHotelId).toList();
            hotelIds.forEach(h -> {
                rate.setHotel(hotelsClient.getHotelsByHotelId(h));
            });
            user.setRatings(ratingByUserId);
            return user;
        }).collect(Collectors.toList());



        return userList;
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on server!!! : ID " + userId));
        //rest call for calling ratings api
        Rating[] userRating = restTemplate
                .getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(userRating).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // call hotels api
            Hotel hotel = restTemplate
                    .getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }
}
