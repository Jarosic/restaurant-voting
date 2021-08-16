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
        List<Restaurant> restaurants = repository.findAll();
        log.info("getAllRestaurants: {}", restaurants);
        return restaurants;
    }

    @Cacheable("restaurants")
    public Restaurant get(int id) {
        Restaurant restaurant = ValidationUtil.checkForExist(repository.findById(id), id, Restaurant.class);
        log.info("get: {}", restaurant);
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
    public void delete(Integer id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }
}
