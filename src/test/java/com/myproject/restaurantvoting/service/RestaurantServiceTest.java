package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.data.MealTestData;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MealService mealService;

    @Autowired
    private UserService userService;

    @Test
    @Override
    public void get() {
        Restaurant expected = restaurantService.get(ID + 1);
        Restaurant actual = RestaurantTestData.BARTOLOMEO;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Override
    public void getAll() {
        List<Restaurant> expected = restaurantService.getAll();
        List<Restaurant> actual = RestaurantTestData.restaurants;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Override
    public void create() {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        restaurantService.create(newRestaurant, USER_ID);

        Restaurant expected = restaurantService.get(ID + 12);

        mealService.create(MealTestData.MEAL_PIVOMAN_1, expected.getId());
        mealService.create(MealTestData.MEAL_PIVOMAN_2, expected.getId());
        mealService.create(MealTestData.MEAL_PIVOMAN_3, expected.getId());

        Restaurant actual = RestaurantTestData.getNew();
        actual.setId(100012);
        actual.setMeals(MealTestData.MEAL_LIST_PIVOMAN);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Override
    public void update() {
        Restaurant update = RestaurantTestData.getUpdate();
        restaurantService.update(update, null);
        Restaurant expected = restaurantService.get(ID + 3);
        Assertions.assertEquals(expected, update);
    }

    @Test
    public void updateWithUser() {
        Restaurant update = RestaurantTestData.getUpdate();
        restaurantService.update(update, ID);
        User user = userService.get(ID);
        user.setVotingDateTime(LocalDateTime.now());
        user.setRestaurant(update.getId());
        userService.update(user, ID);
        update.setUser(user);
        Restaurant expected = restaurantService.get(ID + 3);
        Assertions.assertEquals(expected, update);
    }

    @Test
    @Override
    public void delete() {
        restaurantService.delete(ID + 1);
        Assertions.assertNull(restaurantService.get(ID + 1));
    }

}