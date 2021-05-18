package com.myproject.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;


@NamedQueries({
        @NamedQuery(name = Meal.GET_ALL,
                query = "SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId"),
        @NamedQuery(name = Meal.DELETE,
                query = "DELETE FROM Meal m where m.id=:id")
})

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {

    public static final String GET_ALL = "Meal.getAll";
    public static final String DELETE = "Meal.delete";

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    public Meal(Integer id, String description, int price) {
        super(id);
        this.description = description;
        this.price = price;
    }

    public Meal(String description, int price) {
        this(null, description, price);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", description=" + description +
                ", price=" + price +
                '}';
    }
}
