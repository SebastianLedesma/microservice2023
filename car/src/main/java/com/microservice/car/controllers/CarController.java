package com.microservice.car.controllers;

import com.microservice.car.model.entities.Car;
import com.microservice.car.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll(){
        List<Car> carList = this.carService.getAll();
        if(carList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Integer id) {
        Car car = this.carService.getById(id);
        if(car == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping()
    public ResponseEntity<Car> save(@RequestBody Car car) {
        Car carSave = this.carService.save(car);
        return new ResponseEntity<>(carSave, HttpStatus.CREATED);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable Integer userId){
        List<Car> carList = this.carService.findByUserId(userId);
        if(carList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carList);
    }
}
