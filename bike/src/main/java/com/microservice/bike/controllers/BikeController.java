package com.microservice.bike.controllers;

import com.microservice.bike.model.entities.Bike;
import com.microservice.bike.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll(){
        List<Bike> bikeList = this.bikeService.getAll();
        if(bikeList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getById(@PathVariable Integer id) {
        Bike bike = this.bikeService.getById(id);
        if(bike == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping()
    public ResponseEntity<Bike> save(@RequestBody Bike bike) {
        Bike bikeSave = this.bikeService.save(bike);
        return new ResponseEntity<>(bikeSave, HttpStatus.CREATED);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable Integer userId){
        List<Bike> bikeList = this.bikeService.findByUserId(userId);
        if(bikeList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikeList);
    }

}
