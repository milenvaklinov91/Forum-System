package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Helper {
    public static User createMockAdmin() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setEmail("mock@user.com");
        return mockUser;
    }

    public static PhoneNumber createPhoneNumber() {
        var mockPhoneNumber = new PhoneNumber();
        mockPhoneNumber.setPhoneNumberId(1);
        mockPhoneNumber.setPhoneNumber("+359888123456");
        return mockPhoneNumber;
    }

    public static PostFilterOptions createMockFilterOptions() {
        return new PostFilterOptions(
                "title",
                "content",
                "2023-07-19",
                10,
                1,
                "mostRecently",
                "mostComment",
                "topLiked",
                "sorted",
                "sortOrder");
    }

    public static TagTypes createTag() {
        var tag = new TagTypes();
        tag.setTagTypeId(1);
        tag.setType("Type");
        return tag;
    }

    public static Post createMockPost() {
        var mockPost = new Post();
        mockPost.setPostId(1);
        mockPost.setTitle("Title Post");
        mockPost.setContent("Content post it should be between 32 and 3222 symbols");
        mockPost.setCreateTime(LocalDate.EPOCH.now());
        mockPost.setCreatedBy(createMockUser());
        mockPost.setTag(createTag());
        return mockPost;
    }

    public static Comment createMockComment() {
        var mockComment = new Comment();
        mockComment.setCommentId(1);
        mockComment.setComment("Comment should be between 32 and 3222 symbols");
        mockComment.setCreatedByUser(createMockUser());
        return mockComment;
    }

    public static Vote createVote() {
        Post post = createMockPost();
        VoteTypes voteTypes = type();
        User user = createMockUser();
        var mockVote = new Vote();
        mockVote.setPost(post);
        mockVote.setType(voteTypes);
        mockVote.setCreatedBy(user);
        return mockVote;
    }

    public static VoteDto createVoteDto() {
        Post post = createMockPost();
        VoteTypes voteTypes = type();
        User user = createMockUser();
        var mockVote = new VoteDto();
        mockVote.setPostId(post.getPostId());
        mockVote.setType(voteTypes.getVoteTypeID());
        mockVote.setUserId(user.getId());
        return mockVote;
    }

    public static VoteTypes type() {
        var mockVoteType = new VoteTypes();
        mockVoteType.setVoteTypeID(1);
        mockVoteType.setType("like");
        return mockVoteType;
    }
///////////Comment Tests Section//////////////////
    public static FilterOptionsComment createMockFilterOptionsComment() {
        return new FilterOptionsComment(
                "username",
                "2023-07-19",
                1,
                "sortBy",
                "sortOrder",
                "mostLiked",
                "mostDisliked");
    }
    public static Comment createMockCommentComment() {
        var mockComment = new Comment();
        mockComment.setCommentId(1);
        mockComment.setComment("Comment should be between 32 and 8192 symbols");
        mockComment.setCreateTime(LocalDateTime.now());
        mockComment.setCreatedByUser(createMockUser());
        mockComment.setPostId(createMockPost());
        return mockComment;
    }

    public static VoteComment createVoteComment() {
        Comment comment = createMockCommentComment();
        VoteTypes voteTypes = type();
        User user = createMockUser();
        var mockVoteComment = new VoteComment();
        mockVoteComment.setComment(comment);
        mockVoteComment.setTypeId(voteTypes);
        mockVoteComment.setCreatedBy(user);
        return mockVoteComment;
    }

}
