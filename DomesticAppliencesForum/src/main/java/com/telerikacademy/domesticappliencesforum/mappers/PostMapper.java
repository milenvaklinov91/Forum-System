package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private PostService postService;

    private final TagTypesService tagTypesService;

    @Autowired
    public PostMapper(PostService postService, TagTypesService tagTypesService) {
        this.postService = postService;
        this.tagTypesService = tagTypesService;
    }

    public Post fromPostDto(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTag(tagTypesService.getById(postDto.getTagTypeID()));
        return post;
    }

    public Post fromDto(int id, PostDto postDto) {
        Post post = fromPostDto(postDto);
        post.setPostId(id);
        Post repositoryPost = postService.getById(id);
        post.setCreatedBy(repositoryPost.getCreatedBy());
        return post;
    }

    public PostDto toDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setTagTypeID(post.getTag().getTagTypeId());
        return postDto;
    }


}
