package com.myproject.restaurantvoting.controller.meal;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.service.MealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractMealController {

    @Autowired
    protected MealService service;

    public Meal get(int id) {
        Meal meal = service.get(id);
        log.info("GET get meal by id: {}, meal: {}",id, meal);
        return meal;
    }

    public Meal create(Meal meal, int restaurantId) {
        log.info("POST create meal: {}", meal);
        return service.create(meal, restaurantId);
    }

    public Meal update(Meal meal, int restaurantId, int id) {
        log.info("PUT update meal: {}, id: {}", meal, id);
        return service.update(meal, restaurantId);
    }

    public void delete(Integer id) {
        log.info("DELETE delete meal id: {}", id);
        service.delete(id);
    }
}
