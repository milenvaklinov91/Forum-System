package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.UserMapper;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.user.UserDto;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserServiceImpl service;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserMvcController(UserServiceImpl service, UserMapper userMapper,
                             AuthenticationHelper authenticationHelper) {

        this.service = service;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        List<User> users = service.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    /*@GetMapping("/count")
    public String countAllUsers() {
        List<User> users = service.countAllUsers();
        return null;
    }*/

    @GetMapping("/{id}")
    public String  getSingleUser(@PathVariable int id,Model model) {
        try {
            User user = service.getById(id);
            model.addAttribute("user", user);
            return "user";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping("/new")
    public String showNewUserPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "user-new";
    }

    /*@PostMapping("/new")
    public String createUser(@Valid @ModelAttribute ("user") UserDto userDto, BindingResult errors){
        if (errors.hasErrors()){
            return "user-new";
        }
        try {
            User user = userMapper.fromUserDto(userDto);
            service.create(user);
            return "redirect:/users";
        } catch (EntityDuplicateException e){
            errors.rejectValue("username","username_exists",e.getMessage());
            return "user-new";
        }
    }*/



   /* @GetMapping("/username")
    public String getUserByUsername(@RequestHeader HttpHeaders headers, String username , Model model) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            model.addAttribute("user", user);
            return "user";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/firstname")
    public User getUserByFirstName(@RequestHeader HttpHeaders headers, String firstName) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestHeader HttpHeaders headers, String email) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    *//*@GetMapping("/{id}/all-posts")
    public List<Post> getAllPost(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.getUserDetails(user.getId(), user);
        Set<Post> allPost = (getUserById(id).getPost());
        return new ArrayList<>(allPost);
    }

    @GetMapping("/{id}/all-comments")
    public List<Comment> getAllComments(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.getUserDetails(user.getId(), user);
        Set<Comment> allComment = (getUserById(id).getComments());
        return new ArrayList<>(allComment);
    }*//*
    @GetMapping("/{id}/liked-posts")
    public List<Post> getLikedPostsByUser(@PathVariable int id) {
        return service.getLikedPostsByUser(id);
    }

    @GetMapping("/{id}/disLiked-posts")
    public List<Post> getDisLikedPostsByUser(@PathVariable int id) {
        return service.getDisLikedPostsByUser(id);
    }


    @PutMapping("/{id}")
    public String update(@RequestHeader HttpHeaders headers,@PathVariable int id,
                         @Valid @RequestBody UserDto userDto) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(headers);
            User user = userMapper.fromUserDto(id,userDto);
            service.update(loggedUser,user);
            return "User was successfully updated!";
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/block")
    public User blockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.blockUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/unblock")
    public User unBlockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.unBlockUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/make-admin")
    public User makeAdmin(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.makeAdmin(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/unmake-admin")
    public User unMakeAdmin(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.unMakeAdmin(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
*/

}
