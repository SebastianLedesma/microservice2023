package com.microservice.car.services;

import com.microservice.car.model.entities.Car;

import java.util.List;

public interface CarService {

    List<Car> getAll();

    Car getById(Integer id);

    Car save(Car car);

    List<Car> findByUserId(Integer userId);
}
