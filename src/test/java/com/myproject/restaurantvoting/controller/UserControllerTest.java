package com.myproject.restaurantvoting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.restaurantvoting.controller.user.UserController;
import com.myproject.restaurantvoting.data.RestaurantTestData;
import com.myproject.restaurantvoting.data.UserTestData;
import com.myproject.restaurantvoting.model.Restaurant;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private final Integer USER_ID = 100000;
    private final String REST_URL = "/api/admin/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void getAll() throws Exception {
        when(userService.getAll()).thenReturn(UserTestData.getUserList());
        MvcResult result = mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(UserTestData.getUserList());
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void create() throws Exception {
        User newUser = UserTestData.getNew();
        newUser.setId(USER_ID + 12);

        when(userService.create(newUser)).thenReturn(newUser);

        MvcResult result = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(jsonPath("$.roles").value("USER"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String created = objectMapper.writeValueAsString(newUser);
        Assertions.assertEquals(created, actual);
    }

    @Test
    public void getById() throws Exception {
        String url = REST_URL + "/" + USER_ID;
        User user = UserTestData.USER;

        when(userService.get(USER_ID)).thenReturn(user);

        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getByEmail() throws Exception {
        User admin = UserTestData.ADMIN;
        String url = REST_URL + "/by?email=" + admin.getEmail();

        when(userService.getByEmail(admin.getEmail())).thenReturn(admin);

        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@gmail.com"))
                .andDo(print())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(admin);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void update() throws Exception {
        String url = REST_URL + "/" + USER_ID;
        User updated = UserTestData.getUpdate();
        updated.setId(USER_ID);
        updated.setRestaurantId(100002);

        when(userService.update(updated, USER_ID)).thenReturn(updated);

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(jsonPath("$.email").value("update@gmail.com"))
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(updated);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void delete() throws Exception {
        String url = REST_URL + "/" + USER_ID;

        when(userService.delete(USER_ID)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void vote() throws Exception {
        String url = REST_URL + "/vote?restaurantId=" + RestaurantTestData.BARTOLOMEO.id();

        Restaurant restaurant = RestaurantTestData.BARTOLOMEO;
        LocalDateTime voteDateTime = LocalDateTime.now();
        User userWithVote = UserTestData.getUpdateWithVote(voteDateTime, restaurant.getId());

        when(userService.vote(UserTestData.USER, restaurant.getId(), voteDateTime)).thenReturn(userWithVote);

        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithVote)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
