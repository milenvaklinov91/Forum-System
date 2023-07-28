package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.repositories.CommentRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.CommentRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTests {

    @Mock
    CommentRepositoryImpl commentRepository;

    @InjectMocks
    CommentServiceImpl commentService;
}
