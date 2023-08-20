package com.microservice.User.feignClients;

import com.microservice.User.model.entities.Bike;
import com.microservice.User.model.entities.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service", path = "/bikes")
public interface BikeFeignClient {

    @PostMapping()
    Bike saveBike(@RequestBody Bike bike);

    @GetMapping("/byUser/{userId}")
    List<Bike> getByUserId(@PathVariable Integer userId);
}
