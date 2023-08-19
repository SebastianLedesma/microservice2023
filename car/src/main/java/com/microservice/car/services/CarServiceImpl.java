package com.microservice.car.services;

import com.microservice.car.model.entities.Car;
import com.microservice.car.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getAll() {
        return this.carRepository.findAll();
    }

    @Override
    public Car getById(Integer id) {
        return this.carRepository.findById(id).orElse(null);
    }

    @Override
    public Car save(Car car) {
        return this.carRepository.save(car);
    }

    @Override
    public List<Car> findByUserId(Integer userId) {
        return this.carRepository.findByUserId(userId);
    }
}
