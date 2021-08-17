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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest{

    private final Integer USER_ID = 100000;
    private final String REST_URL = "/api/admin/users";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAll() throws Exception {
        when(userService.getAll()).thenReturn(UserTestData.getUserList());
        MvcResult result = perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(UserTestData.getUserList());
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create() throws Exception {
        User newUser = UserTestData.getNew();
        newUser.setId(USER_ID + 12);
        when(userService.create(any(User.class))).thenReturn(newUser);
        MvcResult result = perform(post(REST_URL)
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
    @WithMockUser(roles = "ADMIN")
    public void getById() throws Exception {
        String url = REST_URL + "/" + USER_ID;
        User user = UserTestData.USER;
        when(userService.get(USER_ID)).thenReturn(user);
        MvcResult result = perform(get(url)
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
    @WithMockUser(roles = "ADMIN")
    public void getByEmail() throws Exception {
        User admin = UserTestData.ADMIN;
        String url = REST_URL + "/by?email=" + admin.getEmail();
        when(userService.getByEmail(admin.getEmail())).thenReturn(admin);
        MvcResult result = perform(get(url)
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
    @WithMockUser(roles = "ADMIN")
    public void update() throws Exception {
        String url = REST_URL + "/" + USER_ID;
        User updated = UserTestData.getUpdate();
        updated.setId(USER_ID);
        updated.setRestaurantId(100002);
        when(userService.update(any(User.class), any(Integer.class))).thenReturn(updated);
        MvcResult result = perform(put(url)
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
    @WithMockUser(roles = "ADMIN")
    public void delete() throws Exception {
        String url = REST_URL + "/" + USER_ID;
        perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }
}
