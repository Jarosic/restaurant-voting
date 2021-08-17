package com.myproject.restaurantvoting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.restaurantvoting.data.UserTestData;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AccountControllerTest extends AbstractControllerTest {
    private final Integer USER_ID = 100000;
    private final String REST_URL = "/api/account";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void register() throws Exception {
        String url = REST_URL + "/register";
        User newUser = UserTestData.getNew();
        when(userService.create(any(User.class))).thenReturn(newUser);
        perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(jsonPath("$.roles").value("USER"))
                .andExpect(jsonPath("$.email").value("new@gmail.com"))
                .andExpect(jsonPath("$.name").value("New"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "user@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    public void getUser() throws Exception {
        User user = UserTestData.USER;
        perform(get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value("User"))
                .andExpect(jsonPath("$.email").value("user@gmail.com"))
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "user@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    public void update() throws Exception {
        User updated = UserTestData.getUpdate();
        updated.setId(USER_ID);
        updated.setRestaurantId(100002);
        when(userService.update(any(User.class), anyInt())).thenReturn(updated);
        perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(jsonPath("$.email").value("update@gmail.com"))
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.restaurantId").value(100002))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "user@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @WithMockUser(roles = "USER")
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
