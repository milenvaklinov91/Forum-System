package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.services.CommentService;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import com.telerikacademy.domesticappliencesforum.services.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    private PostService postService;

    private final TagTypesService tagTypesService;

    @Autowired
    public PostMapper( PostService postService, TagTypesService tagTypesService) {
        this.postService = postService;
        this.tagTypesService = tagTypesService;
    }

    public Post fromPostDto(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setLocalDateTime(LocalDateTime.now());
        post.setTags(tagTypesService.get(postDto.getTagTypeID()));
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
