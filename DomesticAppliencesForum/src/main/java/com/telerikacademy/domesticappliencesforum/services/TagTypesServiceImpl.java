package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.repositories.TagTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagTypesServiceImpl implements TagTypesService{
    private final TagTypesRepository tagTypesRepository;

    @Autowired
    public TagTypesServiceImpl(TagTypesRepository tagTypesRepository) {
        this.tagTypesRepository = tagTypesRepository;
    }
    public List<TagTypes> get(){return tagTypesRepository.get();}

    public TagTypes get(int id){return  tagTypesRepository.get(id);}
}
