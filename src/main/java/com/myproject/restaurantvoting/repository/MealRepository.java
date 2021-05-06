package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.Meal;

public interface MealRepository {

    Meal get(int id);

    Meal save(Meal meal, int restaurantId);

    boolean delete(int id);
}
