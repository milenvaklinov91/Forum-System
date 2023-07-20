package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTests
{
    @Mock
    PostRepositoryImpl postRepository;
    @InjectMocks
    PostServiceImpl postService;

    @Test
    public void testGetAllPosts() {
        // Arrange
        List<Post> expectedPosts = new ArrayList<>();
        when(postRepository.getAllPosts(anyString(), anyString(), anyInt(), anyInt(), anyString()))
                .thenReturn(expectedPosts);

        // Act
        List<Post> result = postService.getAllPosts("user", "2023-07-19", 10, 1, "mostComment");

        // Assert
        assertEquals(expectedPosts, result);
    }

    @Test
    public void testCreate() {
        // Arrange
        User mockUser = Helper.createMockUser();
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(mockUser);


        // Act
        postService.create(mockPost, mockUser);

        // Assert
        verify(postRepository).create(mockPost);
    }
//    @Test
//    public Post testGetById()
//    {
//        //Arrange
//        Post mockPost=createMockPost();
//    }


}
