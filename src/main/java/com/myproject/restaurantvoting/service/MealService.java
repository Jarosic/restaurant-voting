package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.repository.MealRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MealService {

    private final MealRepository repository;

    public List<Meal> getAll(int restaurantId) {
        List<Meal> meals = repository.getAll(restaurantId);
        log.info("getAllMeals: {}", meals);
        return meals;
    }

    public Meal get(int id) {
        Meal meal = repository.get(id);
        log.info("getMeal: {}", meal);
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
