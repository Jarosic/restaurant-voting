package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final Logger LOG = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }


    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = repository.getAll();
        LOG.info("getAllRestaurants: {}", restaurants);
        return restaurants;
    }

    public Restaurant get(int id) {
        Restaurant restaurant = repository.get(id);
        LOG.info("get: {}", restaurant);
        return restaurant;
    }

    public Restaurant create(Restaurant restaurant, Integer userId) {
        LOG.info("create {}", restaurant);
        return repository.save(restaurant, userId);
    }

    public Restaurant update(Restaurant restaurant, Integer userId) {
        LOG.info("update {}", restaurant);
        return repository.save(restaurant, userId);
    }

    public boolean delete(int id) {
        LOG.info("delete {}", id);
        return repository.delete(id);
    }
}
