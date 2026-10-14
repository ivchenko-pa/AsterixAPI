package org.example.asterixapi.repository;

import org.example.asterixapi.model.AsterixCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends MongoRepository<AsterixCharacter, String> {
    List<AsterixCharacter> findAsterixCharacterByProfessionContaining(String profession);
}
