package com.myproject.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

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
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    public static final String GET_ALL = "Restaurant.getAll";
    public static final String DELETE = "Restaurant.delete";

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    public Restaurant(Integer id, String name, List<Meal> meals) {
        super(id, name);
        this.meals = meals;
    }
}
