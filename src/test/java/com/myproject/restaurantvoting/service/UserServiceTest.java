package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.data.UserTestData;
import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.error.exceptions.VotingTimeLimitException;
import com.myproject.restaurantvoting.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    @Override
    @Order(1)
    public void get() {
        User expected = service.get(ID);
        User actual = UserTestData.USER;
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("registered")
                .isEqualTo(expected);
    }

    @Test
    @Override
    @Order(2)
    public void getAll() {
        List<User> expected = service.getAll();
        List<User> actual = UserTestData.getUserList();
        assertThat(actual)
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(expected);
    }

    @Test
    @Order(3)
    public void getByEmail() {
        User expected = service.getByEmail("user@gmail.com");
        User actual = UserTestData.USER;
        assertThat(actual).
                usingRecursiveComparison().
                ignoringFields("registered").
                isEqualTo(expected);
    }

    @Test
    @Override
    @Order(4)
    public void create() {
        User newUser = UserTestData.getNew();
        service.create(newUser);
        User expected = service.get(ID + 12);
        newUser.setId(ID + 12);
        assertThat(newUser).usingRecursiveComparison()
                .ignoringFields("registered")
                .isEqualTo(expected);
    }

    @Test
    @Order(5)
    public void voteNew() {
        User update = UserTestData
                .getUpdateWithVote(null, ID + 1);
        service.vote(update.id(), ID + 1, null);
        User expected = service.get(ID);
        update.setVotingDateTime(expected.getVotingDateTime());
        assertThat(update)
                .usingRecursiveComparison()
                .ignoringFields("registered")
                .isEqualTo(expected);
    }

    @Test
    @Order(6)
    public void reVoteWithoutException() {
        LocalDateTime firstVote = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime secondVote = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
        User newVote = UserTestData
                .getUpdateWithVote(firstVote, ID + 1);
        service.vote(newVote.id(), ID + 1, firstVote);

        User reVote = UserTestData
                .getUpdateWithVote(secondVote, ID + 2);
        service.vote(reVote.id(), ID + 2, secondVote);

        User expected = service.get(ID);
        assertThat(reVote)
                .usingRecursiveComparison()
                .ignoringFields("registered")
                .isEqualTo(expected);
    }

    @Test
    @Order(7)
    public void reVoteWithException() {
        LocalDateTime firstVote = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime secondVote = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0));
        User newVote = UserTestData
                .getUpdateWithVote(firstVote, ID + 1);
        service.vote(newVote.id(), ID + 1, firstVote);

        User reVote = UserTestData
                .getUpdateWithVote(secondVote, ID + 2);

        Assertions.assertThrows(VotingTimeLimitException.class,
                () -> service.vote(reVote.id(), ID + 2, secondVote));
    }

    @Test
    @Override
    @Order(8)
    public void delete() {
        service.delete(ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(ID));
    }

    @Test
    @Override
    @Order(9)
    public void update() {
        User update = UserTestData.getUpdate();
        service.update(update, ID);
        User expected = service.get(ID);
        assertThat(update).usingRecursiveComparison()
                .ignoringFields("registered")
                .isEqualTo(expected);
    }
}