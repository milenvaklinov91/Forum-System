package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PostMapper {
    private UserService userService;
    private PostService postService;

    @Autowired
    public PostMapper(UserService userService, PostService postService) {
        this.postService = postService;
        this.userService = userService;
    }

    public Post fromPostDto(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        // post.setAuthorId(postDto.getCreatedBy());
        return post;
    }

    public Post fromDto(int id, PostDto postDto) {
        Post post = fromPostDto(postDto);
        post.setPostId(id);
        Post repositoryPost = postService.browse(id);
        post.setCreatedBy(repositoryPost.getCreatedBy());
        return post;
    }


}
