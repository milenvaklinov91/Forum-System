package com.telerikacademy.domesticappliencesforum.models.filterOptions;

import java.util.Optional;

public class FilterOptionsComment {

    private Optional<Integer> userId;
    private Optional<String> localDate;
    private Optional<Integer> vote;

    public FilterOptionsComment(Integer userId, String localDate, Integer vote) {
        this.userId = Optional.ofNullable(userId);
        this.localDate = Optional.ofNullable(localDate);;
        this.vote = Optional.ofNullable(vote);;
    }

    public Optional<Integer> getUserId() {
        return userId;
    }

    public Optional<String> getLocalDate() {
        return localDate;
    }

    public Optional<Integer> getVote() {
        return vote;
    }
}
