package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.data.MealTestData;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.Meal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private RestaurantService restaurantService;

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
        assertThat(RestaurantTestData.BARTOLOMEO_WITH_NEW_MEAL).isEqualTo(restaurantService.get(ID + 1));
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
        mealService.delete(ID + 2);
        Assertions.assertThrows(NotFoundException.class, () -> mealService.get(ID));
    }
}