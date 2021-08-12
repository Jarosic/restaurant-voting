package com.myproject.restaurantvoting.util;

import com.myproject.restaurantvoting.error.exceptions.IllegalRequestDataException;
import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.AbstractBaseEntity;
import com.myproject.restaurantvoting.model.User;

public class ValidationUtil {

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static <T> void checkForExist(Object entity, Integer id, Class<T> clazz) {
        if (entity == null) {
            throw  new NotFoundException(clazz.getSimpleName() + " with id = " + id + ", is not exist!");
        }
    }

    public static void checkUserByEmail(User user, String email) {
        if (user == null) {
            throw  new NotFoundException("User with email = " + email + ", is not exist!");
        }
    }
}
