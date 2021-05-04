package com.myproject.restaurantvoting.controller.meal;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.service.MealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AbstractMealController {

    @Autowired
    protected MealService service;

    public Meal get(int id) {
        Meal meal = service.get(id);
        log.info("getMeal: {}", meal);
        return meal;
    }

    public Meal create(Meal meal, int restaurantId) {
        log.info("create {}", meal);
        return service.create(meal, restaurantId);
    }

    public void update(Meal meal, int restaurantId, int id) {
        log.info("update meal: {}, id: {}", meal, id);
        service.update(meal, restaurantId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}
