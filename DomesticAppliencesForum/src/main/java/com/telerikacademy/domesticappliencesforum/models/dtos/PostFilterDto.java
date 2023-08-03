package com.telerikacademy.domesticappliencesforum.models.dtos;

public class PostFilterDto {
    private String title;
    private String content;
    private String localDate;
    private Integer lastTen;
    private Integer tagId;
    private String mostRecently;
    private String mostComments;
    private String mostLiked;
    private String sortBy;
    private String sortOrder;

    public PostFilterDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public Integer getLastTen() {
        return lastTen;
    }

    public void setLastTen(Integer lastTen) {
        this.lastTen = lastTen;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getMostRecently() {
        return mostRecently;
    }

    public void setMostRecently(String mostRecently) {
        this.mostRecently = mostRecently;
    }

    public String getMostComments() {
        return mostComments;
    }

    public void setMostComments(String mostComments) {
        this.mostComments = mostComments;
    }

    public String getMostLiked() {
        return mostLiked;
    }

    public void setMostLiked(String mostLiked) {
        this.mostLiked = mostLiked;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
