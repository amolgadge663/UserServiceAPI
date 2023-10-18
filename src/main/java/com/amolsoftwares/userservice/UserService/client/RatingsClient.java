package com.amolsoftwares.userservice.UserService.client;

import com.amolsoftwares.userservice.UserService.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingsClient {

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingByUserId(@PathVariable String userId);
}
