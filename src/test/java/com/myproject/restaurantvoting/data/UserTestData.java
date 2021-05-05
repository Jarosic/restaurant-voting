package com.myproject.restaurantvoting.data;

import com.myproject.restaurantvoting.model.Role;
import com.myproject.restaurantvoting.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myproject.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    private static final Integer USER_ID = START_SEQ;
    private static final Integer ADMIN_ID = START_SEQ + 1;
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Role.USER);
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
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static User getUpdateWithVote(LocalDateTime localDateTime, int restaurantId) {
        User updated = USER;
        updated.setVotingDateTime(localDateTime);
        updated.setRestaurant(restaurantId);
        return updated;
    }
}
