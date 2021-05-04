package com.myproject.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Restaurant.GET_ALL,
                query = "SELECT r FROM Restaurant r ORDER BY r.name"),
        @NamedQuery(name = Restaurant.DELETE,
                query = "DELETE FROM Restaurant r where r.id=:id")
})
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    public static final String GET_ALL = "Restaurant.getAll";
    public static final String DELETE = "Restaurant.delete";

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, List<Meal> meals) {
        super(id, name);
        this.meals = meals;
    }

    public Restaurant(String name) {
        super(null, name);
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + getId() +
                ", name=" + name +
                ", meals: " + meals +
                "}";
    }
}
