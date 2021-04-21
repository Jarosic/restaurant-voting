package com.myproject.restaurantvoting.data;

import com.myproject.restaurantvoting.model.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.myproject.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    private static final Integer ID = START_SEQ;
    public static final Restaurant BARTOLOMEO = new Restaurant(ID + 1, "Bartolomeo", MealTestData.MEAL_LIST_BARTOLOMEO);
    public static final Restaurant BARTOLOMEO_WITH_NEW_MEAL = new Restaurant(ID + 1, "Bartolomeo", MealTestData.MEAL_LIST_BARTOLOMEO_WITH_NEW_MEAL);
    public static final List<Restaurant> restaurants = new ArrayList<>(
            Arrays.asList(
                    BARTOLOMEO,
                    new Restaurant(ID + 3, "Celentano", MealTestData.MEAL_LIST_CELENTANO),
                    new Restaurant(ID + 2, "Khutor", MealTestData.MEAL_LIST_KHUTOR)
            ));

    public static Restaurant getNew() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName("Pivoman");
        return newRestaurant;
    }

    public static Restaurant getUpdate() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName("Sky");
        newRestaurant.setId(ID + 3);
        return newRestaurant;
    }
}
