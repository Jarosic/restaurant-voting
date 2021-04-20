package com.myproject.restaurantvoting.repository;

import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final RestaurantRepository repository;

    public MealRepositoryImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return entityManager
                .createNamedQuery(Meal.GET_ALL)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

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
