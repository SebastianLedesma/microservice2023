package com.microservice.User.services;

import com.microservice.User.feignClients.BikeFeignClient;
import com.microservice.User.feignClients.CarFeignClient;
import com.microservice.User.model.entities.Bike;
import com.microservice.User.model.entities.Car;
import com.microservice.User.model.entities.User;
import com.microservice.User.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<Car> getCars(Integer userId) {
        return this.restTemplate.getForObject("http://localhost:8002/cars/byUser/" + userId, List.class);
    }

    @Override
    public List<Bike> getBikes(Integer userId) {
        return this.restTemplate.getForObject("http://localhost:8003/bikes/byUser/" + userId, List.class);
    }

    @Override
    public Car saveCar(Integer userId, Car car) {
        car.setUserId(userId);
        return this.carFeignClient.save(car);
    }

    @Override
    public Bike saveBike(Integer userId, Bike bike) {
        bike.setUserId(userId);
        return this.bikeFeignClient.saveBike(bike);

    }

    @Override
    public Map<String, Object> getUserAndVehicules(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null){
            result.put("Message","User not exist.");
            return result;
        }
        result.put("User", user);
        List<Car> carList = this.carFeignClient.getByUserId(userId);
        if(carList == null || carList.isEmpty()){
            result.put("Cars","User has not cars");
        }else{
            result.put("Cars",carList);
        }

        List<Bike> bikeList = this.bikeFeignClient.getByUserId(userId);
        if(bikeList == null || bikeList.isEmpty()){
            result.put("Bikes","User has not bikes.");
        }else{
            result.put("Bikes",bikeList);
        }
        return result;
    }
}
