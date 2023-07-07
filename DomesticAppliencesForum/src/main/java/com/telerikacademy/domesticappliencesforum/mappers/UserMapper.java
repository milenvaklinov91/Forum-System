package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User fromUserDto(UserDto userDto) {
        User user = new User();
        user.getLoginDetails().setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.getLoginDetails().setPassword(userDto.getPassword());
        return user;
    }
    //Todo
}
