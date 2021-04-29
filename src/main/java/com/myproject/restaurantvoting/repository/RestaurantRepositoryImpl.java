package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.model.User;
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
    private EntityManager entityManager;

    private final UserRepository userRepository;

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
    public Restaurant save(Restaurant restaurant, Integer userId) {
        if (userId != null) {
            User user = userRepository.get(userId);
            restaurant.setUser(user);
        }
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
