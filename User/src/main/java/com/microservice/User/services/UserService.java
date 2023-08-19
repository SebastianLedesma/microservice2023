package com.microservice.User.services;

import com.microservice.User.model.entities.Bike;
import com.microservice.User.model.entities.Car;
import com.microservice.User.model.entities.User;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    User save(User user);

    List<Car> getCars(Integer userId);

    List<Bike> getBikes(Integer userId);

    Car saveCar(Integer userId, Car car);

    Bike saveBike(Integer userId, Bike bike);

    Map<String, Object> getUserAndVehicules(Integer userId);
}
