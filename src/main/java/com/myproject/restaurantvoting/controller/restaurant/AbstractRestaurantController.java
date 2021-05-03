package com.myproject.restaurantvoting.controller.restaurant;

import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantService service;

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = service.getAll();
        log.info("getAllRestaurants: {}", restaurants);
        return restaurants;
    }

    public Restaurant get(int id) {
        Restaurant restaurant = service.get(id);
        log.info("get: {}", restaurant);
        return restaurant;
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update restaurant {}, id {}", restaurant, id);
        service.update(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}
