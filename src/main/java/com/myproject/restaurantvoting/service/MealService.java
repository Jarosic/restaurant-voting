package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.repository.MealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    private final Logger LOG = LoggerFactory.getLogger(MealService.class);

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAll(int restaurantId) {
        List<Meal> meals = repository.getAll(restaurantId);
        LOG.info("getAllMeals: {}", meals);
        return meals;
    }

    public Meal get(int id) {
        Meal meal = repository.get(id);
        LOG.info("getMeal: {}", meal);
        return meal;
    }

    public Meal create(Meal meal, int restaurantId) {
        LOG.info("create {}", meal);
        return repository.save(meal, restaurantId);
    }

    public Meal update(Meal meal, int restaurantId) {
        LOG.info("update {}", meal);
        return repository.save(meal, restaurantId);
    }

    public boolean delete(int id) {
        LOG.info("delete {}", id);
        return repository.delete(id);
    }
}
