package com.myproject.restaurantvoting.util;

import com.myproject.restaurantvoting.error.exceptions.IllegalRequestDataException;
import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.AbstractBaseEntity;

import java.util.Optional;

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

    public static <T> T checkForExist(Optional<T> opt, Integer id, Class<T> clazz) {
        return opt.orElseThrow(()
                -> new NotFoundException(clazz.getSimpleName() + " with id = " + id + ", is not exist!"));

    }
}
