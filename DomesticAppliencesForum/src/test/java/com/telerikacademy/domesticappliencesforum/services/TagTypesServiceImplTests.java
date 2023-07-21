package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.TagTypesRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockUser;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagTypesServiceImplTests {
    @Mock
    TagTypesRepositoryImpl tagRepository;

    @InjectMocks
    TagTypesServiceImpl tagService;

    @Test
    public void testGetAllTags() {
        // Arrange
        List<TagTypes> expectedTagTypesList = new ArrayList<>();
        expectedTagTypesList.add(Helper.createTag());
        expectedTagTypesList.add(Helper.createTag());

        when(tagRepository.get()).thenReturn(expectedTagTypesList);

        // Act
        List<TagTypes> result = tagService.get();

        // Assert
        assertEquals(expectedTagTypesList.size(), result.size());
        assertTrue(result.containsAll(expectedTagTypesList));
    }

    @Test
    public void testGet_Should_ReturnTagTypeById() {
        // Arrange
        int tagTypeId = 1;
        TagTypes expectedTagType = Helper.createTag();

        when(tagRepository.get(tagTypeId)).thenReturn(expectedTagType);

        // Act
        TagTypes result = tagService.get(tagTypeId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTagType.getTagTypeId(), result.getTagTypeId());
        assertEquals(expectedTagType.getType(), result.getType());
    }


    @Test
    public void testCreate_Should_ThrowEntityDuplicateException_When_TagTypeAlreadyExists() {
        // Arrange
        TagTypes existingTagType = Helper.createTag();

        TagTypes newTagType = Helper.createTag();

        when(tagRepository.getByName(newTagType.getType())).thenReturn(existingTagType);

        // Act & Assert
        assertThrows(EntityDuplicateException.class, () -> tagService.create(newTagType));
    }

    @Test
    public void testCreate_Should_CreateTagType_When_TagTypeDoesNotExist() {
        // Arrange
        TagTypes newTagType = Helper.createTag();

        when(tagRepository.getByName(newTagType.getType())).thenThrow(EntityNotFoundException.class);
        // Act
        tagService.create(newTagType);

        // Assert
        verify(tagRepository).create(newTagType);
    }

    @Test
    public void testFilterByName_Should_ReturnMatchingTagType() {
        // Arrange
        String tagName = "TagType1";
        TagTypes mockTag1 = Helper.createTag();
        TagTypes mockTag2 = Helper.createTag();
        List<TagTypes> tagsList = new ArrayList<>();
        tagsList.add(mockTag1);
        tagsList.add(mockTag2);

        when(tagRepository.filterByName(tagsList, tagName)).thenReturn(mockTag1);

        // Act
        TagTypes result = tagService.filterByName(tagsList, tagName);

        // Assert
        assertNotNull(result);
        assertEquals(mockTag1.getTagTypeId(), result.getTagTypeId());
        assertEquals(mockTag1.getType(), result.getType());
    }

    @Test
    public void testFilterByName_Should_ReturnNull_When_NoMatchingTagType() {
        // Arrange
        String tagName = "TagType1";
        TagTypes mockTag1 = Helper.createTag();
        List<TagTypes> tagsList = new ArrayList<>();
        tagsList.add(mockTag1);


        when(tagRepository.filterByName(tagsList, tagName)).thenReturn(null);

        // Act
        TagTypes result = tagService.filterByName(tagsList, tagName);

        // Assert
        assertNull(result);
    }

    @Test
    public void testDelete_Should_ThrowUnauthorizedException_When_UserIsNotAdmin() {
        // Arrange
        int tagTypeId = 1;
        User nonAdminUser = createMockUser();
        nonAdminUser.setAdmin(false);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> tagService.delete(tagTypeId, nonAdminUser));

        // Verify that tagTypesRepository.delete() is never called
        verify(tagRepository, never()).delete(tagTypeId);
    }

    @Test
    public void testDelete_Should_CallRepositoryDelete_When_UserIsAdmin() {
        // Arrange
        int tagTypeId = 1;
        User adminUser =createMockUser();
        adminUser.setAdmin(true);

        // Act
        tagService.delete(tagTypeId, adminUser);
        verify(tagRepository).delete(tagTypeId);
    }



}
