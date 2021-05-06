package com.myproject.restaurantvoting.repository;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.model.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@AllArgsConstructor
@Transactional(readOnly = true)
public class MealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final RestaurantRepository repository;

    @Override
    public Meal get(int id) {
        return entityManager.find(Meal.class, id);
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int restaurantId) {
        Restaurant restaurant = repository.get(restaurantId);
        meal.setRestaurant(restaurant);
        if (meal.isNew()) {
            entityManager.persist(meal);
            return meal;
        } else {
            return entityManager.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }
}
