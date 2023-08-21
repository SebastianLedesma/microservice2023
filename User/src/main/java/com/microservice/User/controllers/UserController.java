package com.microservice.User.controllers;

import com.microservice.User.model.entities.Bike;
import com.microservice.User.model.entities.Car;
import com.microservice.User.model.entities.User;
import com.microservice.User.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> userList = this.userService.getAll();
        if(userList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        User user = this.userService.getById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user) {
        User userSave = this.userService.save(user);
        return new ResponseEntity<>(userSave, HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable Integer userId) {
        User user = this.userService.getById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> carList = this.userService.getCars(userId);
        return ResponseEntity.ok(carList);
    }


    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/saveCar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId, @RequestBody Car car){
        if(this.userService.getById(userId) == null){
            return ResponseEntity.notFound().build();
        }
        Car carSave = this.userService.saveCar(userId, car);
        return new ResponseEntity<>(carSave, HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable Integer userId) {
        User user = this.userService.getById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikeList = this.userService.getBikes(userId);
        return ResponseEntity.ok(bikeList);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/saveBike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable Integer userId, @RequestBody Bike bike){
        if(this.userService.getById(userId) == null){
            return ResponseEntity.notFound().build();
        }
        Bike bikeSave = this.userService.saveBike(userId, bike);
        return new ResponseEntity<>(bikeSave, HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAll(@PathVariable Integer userId) {
        Map<String,Object> userAndVehicules = this.userService.getUserAndVehicules(userId);
        return new ResponseEntity<>(userAndVehicules, HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable Integer userId, RuntimeException ex) {
        return new ResponseEntity("El usuario " + userId + "tiene los autos en el taller.",HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable Integer userId, @RequestBody Car car, RuntimeException ex) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para un auto.",HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable Integer userId, RuntimeException ex) {
        return new ResponseEntity("El usuario " + userId + " tiene los motos en el taller.",HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(@PathVariable Integer userId, @RequestBody Bike bike, RuntimeException ex) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para una moto.",HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable Integer userId, RuntimeException ex) {
        return  new ResponseEntity("El usuario " + userId + " tiene los vehiculos en el taller.", HttpStatus.OK);
    }

}
