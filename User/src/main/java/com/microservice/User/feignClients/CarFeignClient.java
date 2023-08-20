package com.microservice.User.feignClients;

import com.microservice.User.model.entities.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", path = "/cars")
public interface CarFeignClient {

    @PostMapping()
    Car save(@RequestBody Car car);

    @GetMapping("/byUser/{userId}")
    List<Car> getByUserId(@PathVariable Integer userId);
}
