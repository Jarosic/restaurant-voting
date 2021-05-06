package com.myproject.restaurantvoting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.restaurantvoting.controller.restaurant.RestaurantController;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.service.RestaurantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    private final int ID = 100001;
    private final String REST_URL = "/api/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void getAll() throws Exception {
        when(restaurantService.getAll()).thenReturn(RestaurantTestData.restaurants);
        MvcResult result = mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(RestaurantTestData.restaurants);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setId(ID + 11);

        when(restaurantService.create(newRestaurant)).thenReturn(newRestaurant);

        MvcResult result = mockMvc.perform(post(REST_URL)
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
    public void getById() throws Exception {
        String url = REST_URL + "/" + ID;
        Restaurant restaurant = RestaurantTestData.BARTOLOMEO;

        when(restaurantService.get(ID)).thenReturn(restaurant);

        MvcResult result = mockMvc.perform(get(url)
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
    public void update() throws Exception {
        String url = REST_URL + "/" + ID;
        Restaurant updated = RestaurantTestData.getUpdate();
        updated.setId(ID);

        when(restaurantService.update(updated)).thenReturn(updated);

        MvcResult result = mockMvc.perform(put(url)
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
    public void delete() throws Exception {
        String url = REST_URL + "/" + ID;

        when(restaurantService.delete(ID)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }
}
