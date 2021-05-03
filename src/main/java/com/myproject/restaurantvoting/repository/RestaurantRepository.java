package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);


}
