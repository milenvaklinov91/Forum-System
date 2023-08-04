package com.telerikacademy.domesticappliencesforum.models.filterOptions;

import java.util.Optional;

public class PostFilterOptions {

    private Optional<String> title;
    private Optional<String> username;
    private Optional<String> localDate;
    private Optional<Integer> lastTen;
    private Optional<Integer> tagId;
    private Optional<String> mostRecently;
    private Optional<String> mostComments;
    private Optional<String> mostLiked;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public PostFilterOptions() {
        this(null, null, null, null,null,null ,
                null, null, null, null);
    }

    public PostFilterOptions(String title, String username,
                             String localDate,
                             Integer lastTen,
                             Integer tagId, String mostRecently,
                             String mostComments,
                             String mostLiked,
                             String sortBy, String sortOrder) {
        this.title = Optional.ofNullable(title);
        this.username = Optional.ofNullable(username);
        this.localDate = Optional.ofNullable(localDate);
        this.lastTen = Optional.ofNullable(lastTen);
        this.tagId = Optional.ofNullable(tagId);
        this.mostRecently = Optional.ofNullable(mostRecently);
        this.mostComments = Optional.ofNullable(mostComments);
        this.mostLiked = Optional.ofNullable(mostLiked);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);

    }

    public Optional<String> getTitle() {
        return title;
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

    public Optional<String> getMostRecently() {
        return mostRecently;
    }

    public Optional<String> getMostComments() {
        return mostComments;
    }

    public Optional<String> getMostLiked() {
        return mostLiked;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public void setMostRecently(Optional<String> mostRecently) {
        this.mostRecently = mostRecently;
    }

    public void setMostComments(Optional<String> mostComments) {
        this.mostComments = mostComments;
    }

    public void setMostLiked(Optional<String> mostLiked) {
        this.mostLiked = mostLiked;
    }
}
