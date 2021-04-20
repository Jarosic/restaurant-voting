package com.myproject.restaurantvoting.util.exception;

public class VotingTimeLimitException extends RuntimeException{
    public VotingTimeLimitException() {
        super("Vote limit exceeded!");
    }
}
