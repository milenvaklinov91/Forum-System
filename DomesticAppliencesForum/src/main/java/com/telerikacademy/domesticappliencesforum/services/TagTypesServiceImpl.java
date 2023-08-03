package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.TagTypesRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagTypesServiceImpl implements TagTypesService {
    private final TagTypesRepository tagTypesRepository;
    private final UserService userService;

    @Autowired
    public TagTypesServiceImpl(TagTypesRepository tagTypesRepository, UserService userService) {
        this.tagTypesRepository = tagTypesRepository;

        this.userService = userService;
    }

    public List<TagTypes> get() {
        return tagTypesRepository.get();
    }

    public TagTypes getById(int id) {
        return tagTypesRepository.get(id);
    }

    public void create(TagTypes tag,User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        boolean duplicateExists = true;
        try {
            tagTypesRepository.getByName(tag.getType());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("Tag", tag.getType());
        }
        tagTypesRepository.create(tag);
    }

    public TagTypes filterByName(List<TagTypes> tags, String name) {
        return tagTypesRepository.filterByName(tags, name);
    }

    public void delete(int id, User user) {
        if (!(user.isAdmin())) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        tagTypesRepository.delete(id);
    }
}
