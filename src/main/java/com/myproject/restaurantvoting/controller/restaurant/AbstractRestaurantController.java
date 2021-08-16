package com.myproject.restaurantvoting.controller.restaurant;

import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.security.SecurityUser;
import com.myproject.restaurantvoting.service.RestaurantService;
import com.myproject.restaurantvoting.service.UserService;
import com.myproject.restaurantvoting.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected UserService userService;

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();
        log.info("GET get all restaurants Restaurants: {}", restaurants);
        return restaurants;
    }

    public Restaurant get(int id) {
        Restaurant restaurant = restaurantService.get(id);
        log.info("GET get restaurant by id: {}, restaurant: {}",id, restaurant);
        return restaurant;
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("POST create restaurant: {}", restaurant);
        return restaurantService.create(restaurant);
    }

    public Restaurant update(Restaurant restaurant, int id) {
        log.info("PUT update restaurant {}, id {}", restaurant, id);
        return restaurantService.update(restaurant);
    }

    public void delete(Integer id) {
        log.info("DELETE delete restaurant id:{}", id);
        restaurantService.delete(id);
    }

    public User vote(SecurityUser authUser, int restaurantId) {
        log.info("PATCH vote restaurantID: {}", restaurantId);
        restaurantService.get(restaurantId);//check for exist
        return userService.vote(authUser.id(), restaurantId, LocalDateTime.now());
    }
}
