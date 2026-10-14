package org.example.asterixapi.service;

import org.example.asterixapi.dto.AsterixCharacterDto;
import org.example.asterixapi.model.AsterixCharacter;
import org.example.asterixapi.repository.CharacterRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    @Test
    void getAllCharacters_shouldReturnListOfTwoCharacters_whenThereAreTwoInRepo() {
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);
        AsterixCharacter character_one = new AsterixCharacter("1", "Bob", 20, "Ing");
        AsterixCharacter character_two = new AsterixCharacter("2", "John", 30, "Dev");
        List<AsterixCharacter> expectedList = List.of(character_one, character_two);
        when(mockRepo.findAll()).thenReturn(expectedList);
        //WHEN
        List<AsterixCharacter> actualList = service.getAllCharacters();

        //THEN
        assertEquals(expectedList, actualList);
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void getAllCharacters_shouldReturnEmptyList_whenNothingInRepo() {
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);
        List<AsterixCharacter> expectedList = List.of();
        when(mockRepo.findAll()).thenReturn(expectedList);

        //WHEN
        List<AsterixCharacter> actualList = service.getAllCharacters();

        //THEN
        assertEquals(expectedList, actualList);
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void getAsterixCharacterById_shounldBeReturnedOneCharackter_whenThisExists() {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);
        AsterixCharacter expectedAsterixCharacter = new AsterixCharacter("1", "Bob", 20, "Ing");
        Optional<AsterixCharacter> expectedOptional = Optional.of(expectedAsterixCharacter);
        when(mockRepo.findById("1")).thenReturn(expectedOptional);

        //WHEN
        AsterixCharacter actualAsterixCharacter = service.getAsterixCharacterById("1");

        //THEN
        assertEquals(expectedAsterixCharacter, actualAsterixCharacter);
    }

    @Test
    void getAsterixCharacterById_shounldBeReturnedNull_whenThisNotExists() {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);
        AsterixCharacter expectedAsterixCharacter = null;
        Optional<AsterixCharacter> expectedOptional = Optional.empty();
        when(mockRepo.findById("1")).thenReturn(expectedOptional);

        //WHEN
        AsterixCharacter actualAsterixCharacter = service.getAsterixCharacterById("1");

        //THEN
        assertEquals(expectedAsterixCharacter, actualAsterixCharacter);
    }

    @Test
    void updateAsterixCharacter_shouldBeReturnedOne_whenOneExists() throws Exception {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);
        AsterixCharacterDto inputAsterixCharacterDto = new AsterixCharacterDto("John", 33, "QA");
        AsterixCharacter expectedAsterixCharacter = new AsterixCharacter("1", "John", 33, "QA");
        when(mockRepo.existsById("1")).thenReturn(true);

        //WHEN
        AsterixCharacter actualAsterixCharacter = service.updateAsterixCharacter("1", inputAsterixCharacterDto);

        //THEN
        assertEquals(expectedAsterixCharacter, actualAsterixCharacter);

        verify(mockRepo, times(1)).save(expectedAsterixCharacter
                .withName(inputAsterixCharacterDto.name())
                .withAge(inputAsterixCharacterDto.age())
                .withProfession(inputAsterixCharacterDto.profession())
        );
    }

    @Test
    void updateAsterixCharacterr_shouldBeException_whenNoneExists() {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);
        AsterixCharacterDto inputAsterixCharacterDto = new AsterixCharacterDto("John", 33, "QA");
        when(mockRepo.existsById("1")).thenReturn(false);
        //WHEN
        try {
            AsterixCharacter actualAsterixCharacter = service.updateAsterixCharacter("1", inputAsterixCharacterDto);
            fail("Expected Exception not thrown, even though character with id: 1, that does not exist.");
        } catch (Exception e) {
            assert (true);
        }
    }

    @Test
    void deleteAsterixCharacter_shouldBeTrue_whenCharacterExist() {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);

        when(mockRepo.existsById("1")).thenReturn(true);

        //WHEN
        boolean actualResult = service.deleteAsterixCharacter("1");

        //THEN
        assertEquals(true, actualResult);
        verify(mockRepo, times(1)).existsById("1");
        verify(mockRepo, times(1)).deleteById("1");
    }

    @Test
    void deleteAsterixCharacter_shouldBeFalse_whenNoneCharacterExist() {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);

        when(mockRepo.existsById("1")).thenReturn(false);

        //WHEN
        boolean actualResult = service.deleteAsterixCharacter("1");

        //THEN
        assertEquals(false, actualResult);
        verify(mockRepo, times(1)).existsById("1");
        verify(mockRepo, times(0)).deleteById("1");
    }

    @Test
    void deleteAsterixCharacter_shouldBeFalse_whenIdIsNull() {
        //GIVEN
        CharacterRepository mockRepo = mock(CharacterRepository.class);
        IdService mockIdService = mock(IdService.class);
        AsterixService service = new AsterixService(mockRepo, mockIdService);

        //WHEN
        boolean actualResult = service.deleteAsterixCharacter(null);

        //THEN
        assertEquals(false, actualResult);
        verify(mockRepo, times(0)).existsById("1");
        verify(mockRepo, times(0)).deleteById("1");
    }
}