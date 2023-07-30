package com.telerikacademy.domesticappliencesforum.models.filterOptions;

import java.util.Optional;

public class FilterOptionsComment {

    private Optional<String> username;
    private Optional<String> localDate;
    private Optional<Integer> vote;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;
    private Optional<String> mostLiked;
    private Optional<String> mostDisliked;



    public FilterOptionsComment() {
        this(null,null,null,null,null, null,null);
    }

    public FilterOptionsComment(Integer username,
                                String localDate,
                                Integer vote,
                                String sortBy,
                                String sortOrder,
                                String mostLiked,
                                String mostDisliked) {
        this.username = Optional.ofNullable(String.valueOf(username));
        this.localDate = Optional.ofNullable(localDate);
        this.vote = Optional.ofNullable(vote);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
        this.mostLiked = Optional.ofNullable(mostLiked);
        this.mostDisliked = Optional.ofNullable(mostDisliked);

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

    public Optional<String> getMostLiked() {
        return mostLiked;
    }

    public Optional<String> getMostDisliked() {
        return mostDisliked;
    }
}
