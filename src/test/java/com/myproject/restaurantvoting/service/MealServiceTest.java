package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.data.MealTestData;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.model.Meal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void getAll() {
        List<Meal> expected = mealService.getAll(ID + 1);
        List<Meal> actual = MealTestData.MEAL_LIST_BARTOLOMEO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Override
    public void get() {
        Meal expected = mealService.get(ID + 2);
        Meal actual = MealTestData.meal;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Override
    public void create() {
        Meal newMeal = MealTestData.getNew();
        mealService.create(newMeal, ID + 1);
        Meal expected = mealService.get(ID + 12);
        newMeal.setId(ID + 12);
        Assertions.assertEquals(expected, newMeal);
    }

    @Test
    public void getRestaurantWithNewMeal() {
        Meal newMeal = MealTestData.getNew();
        mealService.create(newMeal, ID + 1);
        Assertions.assertEquals(restaurantService.get(ID + 1), RestaurantTestData.BARTOLOMEO_WITH_NEW_MEAL);
    }

    @Test
    @Override
    public void update() {
        Meal update = MealTestData.getUpdate();
        mealService.update(update, ID + 1);
        Meal expected = mealService.get(ID + 2);
        Assertions.assertEquals(expected, update);
    }

    @Test
    @Override
    public void delete() {
        Assertions.assertTrue(mealService.delete(ID + 2));
    }
}