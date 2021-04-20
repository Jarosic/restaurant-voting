package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.Meal;

import java.util.List;

public interface MealRepository {

    List<Meal> getAll(int restaurantId);

    Meal get(int id);

    Meal save(Meal meal, int restaurantId);

    boolean delete(int id);
}
