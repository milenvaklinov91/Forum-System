package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.UserLoginDetails;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromUserDto(UserDto userDto) {
        UserLoginDetails loginDetails = new UserLoginDetails();
        loginDetails.setUsername(userDto.getUsername());
        loginDetails.setPassword(userDto.getPassword());

        User user = new User();
        user.setLoginDetails(loginDetails);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        return user;
    }
    //Todo
}
