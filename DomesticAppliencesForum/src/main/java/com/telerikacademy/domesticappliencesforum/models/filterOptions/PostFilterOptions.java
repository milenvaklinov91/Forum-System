package com.telerikacademy.domesticappliencesforum.models.filterOptions;

import java.util.Optional;

public class PostFilterOptions {

    private Optional<String> username;
    private Optional<String> localDate;
    private Optional<Integer> lastTen;
    private Optional<Integer> tagId;
    private Optional<String> mostComments;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public PostFilterOptions(String username, String localDate,
                             Integer lastTen,
                             Integer tagId, String mostComments,
                             String sortBy, String sortOrder) {
        this.username = Optional.ofNullable(username);
        this.localDate = Optional.ofNullable(localDate);
        this.lastTen = Optional.ofNullable(lastTen);
        this.tagId = Optional.ofNullable(tagId);
        this.mostComments = Optional.ofNullable(mostComments);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);

    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getLocalDate() {
        return localDate;
    }

    public Optional<Integer> getLastTen() {
        return lastTen;
    }

    public Optional<Integer> getTagId() {
        return tagId;
    }

    public Optional<String> getMostComments() {
        return mostComments;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}
