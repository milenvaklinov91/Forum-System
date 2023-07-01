package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private UserService userService;
    @Autowired
    public PostMapper (UserService userService){
        this.userService = userService;
    }

    public Post fromPostDto (PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(post.getContent());
        return post;
    }


}
