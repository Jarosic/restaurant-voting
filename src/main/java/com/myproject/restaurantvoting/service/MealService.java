package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.repository.MealRepository;
import com.myproject.restaurantvoting.repository.RestaurantRepository;
import com.myproject.restaurantvoting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;

    private final RestaurantRepository restaurantRepository;

    public Meal get(int id) {
        Meal meal = ValidationUtil.checkForExist(mealRepository.findById(id), id, Meal.class);
        log.info("getMeal: {}", meal);

        return meal;
    }

    @CachePut("restaurants")
    public Meal create(Meal meal, int restaurantId) {
        log.info("create {}", meal);
        Restaurant restaurant = ValidationUtil
                .checkForExist(restaurantRepository.findById(restaurantId), restaurantId, Restaurant.class);
        meal.setRestaurant(restaurant);
        return mealRepository.save(meal);
    }

    @CachePut("restaurants")
    public Meal update(Meal meal, int restaurantId) {
        log.info("update {}", meal);
        Restaurant restaurant = ValidationUtil
                .checkForExist(restaurantRepository.findById(restaurantId), restaurantId, Restaurant.class);
        meal.setRestaurant(restaurant);
        return mealRepository.save(meal);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(Integer id) {
        log.info("delete {}", id);
        mealRepository.deleteById(id);
    }
}
