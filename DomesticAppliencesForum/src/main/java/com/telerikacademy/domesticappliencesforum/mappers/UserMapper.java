package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.user.RegisterDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.user.UserDto;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    UserService userService;

    public UserMapper(UserService userService) {
        this.userService = userService;
    }

     /*public User fromUserDto(int id , UserDto userDto) {

        User user = fromUserDto(userDto);

        user.setId(id);
        return user;
    }*/

    public User fromDto(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        return user;
    }
}
