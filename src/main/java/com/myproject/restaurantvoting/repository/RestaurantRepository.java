package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> findAll();

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Override
    void deleteById(Integer id);
}
