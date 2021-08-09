package com.myproject.restaurantvoting.controller.restaurant;

import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.security.SecurityUser;
import com.myproject.restaurantvoting.util.ValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "Restaurants Controller")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "api/restaurants";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        Restaurant created = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        return super.update(restaurant, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        return super.delete(id);
    }

    @Override
    @PatchMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User vote(@AuthenticationPrincipal SecurityUser authUser, @RequestParam int restaurantId) {
        return super.vote(authUser, restaurantId);
    }
}
