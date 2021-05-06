package com.myproject.restaurantvoting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.restaurantvoting.controller.meal.MealController;
import com.myproject.restaurantvoting.data.MealTestData;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.model.Meal;
import com.myproject.restaurantvoting.service.MealService;
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

@WebMvcTest(MealController.class)
public class MealControllerTest {

    private final int ID = 100002;
    private final int RESTAURANT_ID = RestaurantTestData.BARTOLOMEO.id();
    private final String REST_URL = "/api/meals";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MealService mealService;

    @Test
    public void create() throws Exception {
        String url = REST_URL + "?restaurantId=" + RESTAURANT_ID;

        Meal newMeal = MealTestData.getNew();
        System.out.println(newMeal.toString());
        newMeal.setId(ID + 10);

        when(mealService.create(newMeal, RESTAURANT_ID)).thenReturn(newMeal);

        MvcResult result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMeal)))
                .andExpect(jsonPath("$.description").value("Coconut"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newMeal);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getById() throws Exception {
        String url = REST_URL + "/" + ID;
        Meal meal = MealTestData.meal;

        when(mealService.get(ID)).thenReturn(meal);

        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(meal);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void update() throws Exception {
        String url = REST_URL + "/" + ID + "?restaurantId=" + RESTAURANT_ID;
        Meal updated = MealTestData.getUpdate();
        updated.setId(ID);

        when(mealService.update(updated, RESTAURANT_ID)).thenReturn(updated);

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(jsonPath("$.description").value("Coca-cola"))
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

        when(mealService.delete(ID)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }
}
