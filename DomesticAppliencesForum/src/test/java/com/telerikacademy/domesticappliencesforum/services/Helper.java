package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;

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
        mockPost.setCreateTime(LocalDateTime.now());
        mockPost.setCreatedBy(createMockUser());
        mockPost.setTag(createTag());
        return mockPost;
    }
}
