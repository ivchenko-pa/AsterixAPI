package org.example.asterixapi.service;

import lombok.AllArgsConstructor;
import org.example.asterixapi.dto.AsterixCharacterDto;
import org.example.asterixapi.model.AsterixCharacter;
import org.example.asterixapi.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class AsterixService {

    private final CharacterRepository repo;
    private final IdService idService;

    public List<AsterixCharacter> getAllCharacters(){

        return repo.findAll();
    }

    public AsterixCharacter getAsterixCharacterById(String q){
        return repo.findById(q).orElse(null);
    }

    public AsterixCharacter createAsterixCharacter (AsterixCharacterDto newAsterixCharacter) {
        AsterixCharacter asterixCharacter = AsterixCharacter.builder()
                .id(idService.randomId())
                .name(newAsterixCharacter.name())
                .age(newAsterixCharacter.age())
                .profession(newAsterixCharacter.profession())
                .build();
        repo.save(asterixCharacter);
        return asterixCharacter;
    }


    public AsterixCharacterDto updateAsterixCharacter(String id, AsterixCharacterDto asterixCharacterDto) {
        AsterixCharacter toBeUpdatedAsterixCharacter = repo.findById(id).orElse(null);

        if(!Objects.isNull(toBeUpdatedAsterixCharacter)){
            repo.save(toBeUpdatedAsterixCharacter.withAge(asterixCharacterDto.age())
                    .withName(asterixCharacterDto.name())
                    .withProfession(asterixCharacterDto.profession()));
        }
        return asterixCharacterDto;

    }

    public boolean deleteAsterixCharacter(String id){
        if (repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

}
