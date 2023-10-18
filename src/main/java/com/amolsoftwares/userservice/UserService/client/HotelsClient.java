package com.amolsoftwares.userservice.UserService.client;

import com.amolsoftwares.userservice.UserService.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelsClient {

    @GetMapping("/hotels/{hotelId}")
    Hotel getHotelsByHotelId(@PathVariable String hotelId);
}
