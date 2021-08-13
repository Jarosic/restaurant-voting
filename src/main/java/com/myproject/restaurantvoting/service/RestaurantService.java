package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.repository.RestaurantRepository;
import com.myproject.restaurantvoting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = repository.getAll();
        log.info("getAllRestaurants: {}", restaurants);
        return restaurants;
    }

    @Cacheable("restaurants")
    public Restaurant get(int id) {
        Restaurant restaurant = repository.get(id);
        log.info("get: {}", restaurant);
        ValidationUtil.checkForExist(restaurant, id, Restaurant.class);
        return restaurant;
    }

    @CachePut("restaurants")
    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        return repository.save(restaurant);
    }

    @CachePut("restaurants")
    public Restaurant update(Restaurant restaurant) {
        log.info("update {}", restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.delete(id);
    }
}
