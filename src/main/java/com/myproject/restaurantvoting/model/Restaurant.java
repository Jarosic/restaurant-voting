package com.myproject.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    public Restaurant(Integer id, String name, List<Meal> meals) {
        super(id, name);
        this.meals = meals;
    }
}
