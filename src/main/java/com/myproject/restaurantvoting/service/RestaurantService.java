package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = repository.getAll();
        log.info("getAllRestaurants: {}", restaurants);
        return restaurants;
    }

    public Restaurant get(int id) {
        Restaurant restaurant = repository.get(id);
        log.info("get: {}", restaurant);
        if (restaurant == null) {
            throw  new NotFoundException("Restaurant with id = " + id + ", is not found!");
        }
        return restaurant;
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        return repository.save(restaurant);
    }

    public Restaurant update(Restaurant restaurant) {
        log.info("update {}", restaurant);
        return repository.save(restaurant);
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.delete(id);
    }
}
