package com.telerikacademy.domesticappliencesforum.models.filterOptions;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.services.UserService;

import java.util.Optional;

public class FilterOptionsComment {

    private Optional<String> username;
    private Optional<String> localDate;
    private Optional<Integer> vote;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public FilterOptionsComment(Integer username, String localDate, Integer vote, String sortBy, String sortOrder) {
        this.username = Optional.ofNullable(String.valueOf(username));
        this.localDate = Optional.ofNullable(localDate);
        this.vote = Optional.ofNullable(vote);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
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

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}
