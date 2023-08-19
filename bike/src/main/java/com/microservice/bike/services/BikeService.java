package com.microservice.bike.services;

import com.microservice.bike.model.entities.Bike;

import java.util.List;

public interface BikeService {

    List<Bike> getAll();

    Bike getById(Integer id);

    Bike save(Bike bike);

    List<Bike> findByUserId(Integer userId);
}
