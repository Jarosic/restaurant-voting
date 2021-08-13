package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.data.MealTestData;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MealService mealService;

    @Test
    @Override
    public void get() {
        Restaurant expected = restaurantService.get(ID + 1);
        Restaurant actual = RestaurantTestData.BARTOLOMEO;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Override
    public void getAll() {
        List<Restaurant> expected = restaurantService.getAll();
        List<Restaurant> actual = RestaurantTestData.restaurants;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Override
    public void create() {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        restaurantService.create(newRestaurant);

        Restaurant expected = restaurantService.get(ID + 12);

        mealService.create(MealTestData.MEAL_PIVOMAN_1, expected.getId());
        mealService.create(MealTestData.MEAL_PIVOMAN_2, expected.getId());
        mealService.create(MealTestData.MEAL_PIVOMAN_3, expected.getId());

        Restaurant actual = RestaurantTestData.getNew();
        actual.setId(100012);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Override
    public void update() {
        Restaurant update = RestaurantTestData.getUpdate();
        restaurantService.update(update);
        Restaurant expected = restaurantService.get(ID + 3);
        assertThat(update).isEqualTo(expected);
    }

    @Test
    @Override
    public void delete() {
        restaurantService.delete(ID + 1);
        Assertions.assertThrows(NotFoundException.class, () -> restaurantService.get(ID + 1));
    }
}