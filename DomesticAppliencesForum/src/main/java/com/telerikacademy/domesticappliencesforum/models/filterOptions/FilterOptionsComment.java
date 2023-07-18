package com.telerikacademy.domesticappliencesforum.models.filterOptions;

import java.util.Optional;

public class FilterOptionsComment {

    private Optional<String> username;
    private Optional<String> localDate;
    private Optional<Integer> vote;

    public FilterOptionsComment(String username, String localDate, Integer vote) {
        this.username = Optional.ofNullable(username);
        this.localDate = Optional.ofNullable(localDate);;
        this.vote = Optional.ofNullable(vote);;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getLocalDate() {
        return localDate;
    }

    public Optional<Integer> getVote() {
        return vote;
    }
}
