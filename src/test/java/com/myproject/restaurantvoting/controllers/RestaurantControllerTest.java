package com.myproject.restaurantvoting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.data.UserTestData;
import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.RestaurantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest extends AbstractControllerTest {

    private final int ID = 100001;
    private final String REST_URL = "/api/restaurants";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    @WithMockUser(roles = "USER")
    public void getAll() throws Exception {
        when(restaurantService.getAll()).thenReturn(RestaurantTestData.restaurants);
        MvcResult result = perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(RestaurantTestData.restaurants);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        when(restaurantService.create(any(Restaurant.class))).thenReturn(newRestaurant);
        MvcResult result = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRestaurant)))
                .andExpect(jsonPath("$.name").value("Pivoman"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newRestaurant);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getById() throws Exception {
        String url = REST_URL + "/" + ID;
        Restaurant restaurant = RestaurantTestData.BARTOLOMEO;
        when(restaurantService.get(ID)).thenReturn(restaurant);
        MvcResult result = perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andDo(print())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(restaurant);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void update() throws Exception {
        String url = REST_URL + "/" + ID;
        Restaurant updated = RestaurantTestData.getUpdate();
        updated.setId(ID);
        when(restaurantService.update(updated)).thenReturn(updated);
        MvcResult result = perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(jsonPath("$.name").value("Sky"))
                .andExpect(jsonPath("$.id").value(ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(updated);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void delete() throws Exception {
        String url = REST_URL + "/" + ID;
        perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithUserDetails(value = "user@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    public void vote() throws Exception {
        String url = REST_URL + "/vote?restaurantId=" + ID;
        User user = UserTestData.getUpdateWithVote(null, ID);
        perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name").value("User"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.restaurantId").value(ID))
                .andDo(print());
    }
}
