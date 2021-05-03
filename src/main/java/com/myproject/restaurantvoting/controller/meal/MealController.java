package com.myproject.restaurantvoting.controller.meal;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.service.MealService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MealController {

    @Autowired
    protected MealService service;

    @GetMapping("api/meals")
    public List<Meal> getAll(@RequestParam int restaurantId) {
        return service.getAll(restaurantId);
    }
}
