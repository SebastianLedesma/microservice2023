package com.microservice.bike.services;

import com.microservice.bike.model.entities.Bike;
import com.microservice.bike.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService{

    @Autowired
    private BikeRepository bikeRepository;

    @Override
    public List<Bike> getAll() {
        return this.bikeRepository.findAll();
    }

    @Override
    public Bike getById(Integer id) {
        return this.bikeRepository.findById(id).orElse(null);
    }

    @Override
    public Bike save(Bike bike) {
        return this.bikeRepository.save(bike);
    }

    @Override
    public List<Bike> findByUserId(Integer userId) {
        return this.bikeRepository.findByUserId(userId);
    }
}
