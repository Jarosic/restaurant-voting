package com.myproject.restaurantvoting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.restaurantvoting.data.UserTestData;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
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
        newUser.setId(USER_ID + 12);
        when(userService.create(any(User.class))).thenReturn(newUser);
        MvcResult result = perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(jsonPath("$.roles").value("USER"))
                .andExpect(jsonPath("$.email").value("new@gmail.com"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String created = objectMapper.writeValueAsString(newUser);
        Assertions.assertEquals(created, actual);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getUser() throws Exception {
        User user = UserTestData.USER;
        //when(userService.get(USER_ID)).thenReturn(user);
        when(userService.get(anyInt())).thenReturn(user);
        MvcResult result = perform(get(REST_URL)
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
    @WithMockUser(roles = "USER")
    public void update() throws Exception {
        User updated = UserTestData.getUpdate();
        updated.setId(USER_ID);
        updated.setRestaurantId(100002);
        when(userService.update(any(User.class), any(Integer.class))).thenReturn(updated);
        MvcResult result = perform(put(REST_URL)
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
    @WithMockUser(roles = "USER")
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }
}
