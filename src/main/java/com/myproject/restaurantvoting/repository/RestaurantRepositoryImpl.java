package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@AllArgsConstructor
@Transactional(readOnly = true)
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Restaurant get(int id) {
        return entityManager.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getAll() {
        return entityManager
                .createNamedQuery(Restaurant.GET_ALL)
                .getResultList();
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            entityManager.persist(restaurant);
            return restaurant;
        } else {
            return entityManager.merge(restaurant);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager
                .createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }
}
