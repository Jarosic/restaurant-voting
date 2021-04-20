package com.myproject.restaurantvoting.util;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.util.exception.VotingTimeLimitException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteUtil {
    private final static LocalTime VOTING_TIME_LIMIT = LocalTime.of(12, 0);

    public static boolean checkVotingTime(LocalDateTime dateTime) throws VotingTimeLimitException {
        return dateTime.toLocalDate().isEqual(LocalDateTime.now().toLocalDate())
                && dateTime.toLocalTime().isBefore(VOTING_TIME_LIMIT);
    }

    public static boolean checkVote(LocalDateTime dateTime) {
       return dateTime == null;
    }

    public static User voteCreateUpdateHelper(User user, int restaurantId, LocalDateTime votingDateTime) {
        User updatedUser = new User(
                user.id(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.getRegistered(),
                user.getRoles()
        );

        if (VoteUtil.checkVote(user.getVotingDateTime())) {
            updatedUser.setVotingDateTime(LocalDateTime.now());
            updatedUser.setRestaurant(restaurantId);
        } else {

            if (VoteUtil.checkVotingTime(votingDateTime)) {
                updatedUser.setVotingDateTime(votingDateTime);
                updatedUser.setRestaurant(restaurantId);
            } else {
                throw new VotingTimeLimitException();
            }
        }
        return updatedUser;
    }
}
