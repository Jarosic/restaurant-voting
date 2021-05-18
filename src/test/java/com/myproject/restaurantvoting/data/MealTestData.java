package com.myproject.restaurantvoting.data;


import com.myproject.restaurantvoting.model.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.myproject.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    private static final Integer ID = START_SEQ + 1;

    public static final List<Meal> MEAL_LIST_BARTOLOMEO = new ArrayList<>(Arrays.asList(
            new Meal(ID + 1, "Milk", 35),
            new Meal(ID + 2, "Cake", 50),
            new Meal(ID + 3, "Ice cream", 20)
    ));
    public static final List<Meal> MEAL_LIST_KHUTOR = new ArrayList<>(Arrays.asList(
            new Meal(ID + 4, "Soup", 25),
            new Meal(ID + 5, "Omelette", 35),
            new Meal(ID + 6, "Salad", 20),
            new Meal(ID + 7, "Juice", 15),
            new Meal(ID + 8, "Bread", 10)
    ));

    public static final List<Meal> MEAL_LIST_CELENTANO = new ArrayList<>(Arrays.asList(
            new Meal(ID + 9, "Pizza", 120),
            new Meal(ID + 10, "Beer", 50)

    ));

    public static final List<Meal> MEAL_LIST_BARTOLOMEO_WITH_NEW_MEAL = new ArrayList<>(Arrays.asList(
            new Meal(ID + 1, "Milk", 35),
            new Meal(ID + 2, "Cake", 50),
            new Meal(ID + 3, "Ice cream", 20),
            new Meal(ID + 11, "Coconut", 100)
    ));

    public static final Meal MEAL_PIVOMAN_1 = new Meal( "El", 40);
    public static final Meal MEAL_PIVOMAN_2 = new Meal( "Fish", 15);
    public static final Meal MEAL_PIVOMAN_3 = new Meal( "Ice cream", 20);


    public static Meal meal = new Meal(ID + 1, "Milk", 35);

    public static Meal getUpdate() {
        meal.setId(ID + 1);
        meal.setDescription("Coca-cola");
        meal.setPrice(35);
        return meal;
    }

    public static Meal getNew() {
        return new Meal("Coconut", 100);
    }
}
