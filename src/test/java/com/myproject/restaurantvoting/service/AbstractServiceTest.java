package com.myproject.restaurantvoting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.myproject.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:database/populateDB.sql")
public abstract class AbstractServiceTest {

    public static final Integer ID = START_SEQ;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    public void cacheEvict() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}
