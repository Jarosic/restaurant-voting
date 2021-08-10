package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.repository.MealRepository;
import com.myproject.restaurantvoting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MealService {

    private final MealRepository repository;

    public Meal get(int id) {
        Meal meal = repository.get(id);
        log.info("getMeal: {}", meal);
        ValidationUtil.checkForExist(meal, id, Meal.class);
        return meal;
    }

    public Meal create(Meal meal, int restaurantId) {
        log.info("create {}", meal);
        return repository.save(meal, restaurantId);
    }

    public Meal update(Meal meal, int restaurantId) {
        log.info("update {}", meal);
        return repository.save(meal, restaurantId);
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.delete(id);
    }
}
