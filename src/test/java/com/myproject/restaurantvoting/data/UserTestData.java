package com.myproject.restaurantvoting.data;

import com.myproject.restaurantvoting.model.Role;
import com.myproject.restaurantvoting.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.myproject.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    private static final Integer USER_ID = START_SEQ;
    private static final Integer ADMIN_ID = START_SEQ + 1;
    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "$2y$12$hmZpS1PiTi8c8cmhvZysdONis9H/OC0ogDRwUXk7p7wtAEIS2CWMW", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "$2y$12$n.ZyeC/XZhymQUZskag7j.29xuAQLA2IBLrk7svD6p.28.yMfLIdS", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "$2y$12$tjAw8LHNY2C.pKDZkfYTj.E3YTmYws2cG6urB04sHQRcvgJ.6Uaza", Role.USER);
    }

    public static List<User> getUserList() {
        List<User> users = new ArrayList<>();
        users.add(ADMIN);
        users.add(USER);
        return users;
    }

    public static User getUpdate() {
        User updated = USER;
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("test");
        return updated;
    }

    public static User getUpdateWithVote(LocalDateTime localDateTime, int restaurantId) {
        User updated = USER;
        updated.setVotingDateTime(localDateTime);
        updated.setRestaurantId(restaurantId);
        return updated;
    }
}
