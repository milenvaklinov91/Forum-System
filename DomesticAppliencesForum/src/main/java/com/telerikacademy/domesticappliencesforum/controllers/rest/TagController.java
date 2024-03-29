package com.telerikacademy.domesticappliencesforum.controllers.rest;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagTypesService tagTypesService;
    private final AuthenticationHelper authenticationHelper;

    public TagController(TagTypesService tagTypesService, AuthenticationHelper authenticationHelper) {
        this.tagTypesService = tagTypesService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<TagTypes> getAllTags() {
        return tagTypesService.get();
    }

    @GetMapping("/{type}")
    public TagTypes filterByName(@PathVariable String type) {
        List<TagTypes> tags = tagTypesService.get();
        return tagTypesService.filterByName(tags, type);
    }

    @PostMapping
    public TagTypes create(@Valid @RequestBody TagTypes tag, @RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            tagTypesService.create(tag, user);
            return tag;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            tagTypesService.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
