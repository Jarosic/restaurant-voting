package com.myproject.restaurantvoting.repository;

import com.myproject.restaurantvoting.util.SecurityUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.myproject.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:database/populateDB.sql")
public abstract class AbstractServiceTest {

    public static final Integer ID = START_SEQ;
    public static final Integer USER_ID = SecurityUtil.getUserId();

    public void get() {};

    public void getAll(){};

    public void create(){};

    public void update(){};

    public void delete(){};
}
