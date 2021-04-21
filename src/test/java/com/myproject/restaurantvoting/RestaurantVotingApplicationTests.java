package com.myproject.restaurantvoting;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RestaurantVotingApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void get() {
        User user = userService.get(100001);
    }

    @Test
    void getAll() {
        List users = userService.getAll();
    }

}
