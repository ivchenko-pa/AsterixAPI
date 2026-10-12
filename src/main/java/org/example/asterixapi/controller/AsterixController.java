package org.example.asterixapi.controller;

import lombok.AllArgsConstructor;
import org.example.asterixapi.model.AsterixCharacter;
import org.example.asterixapi.repository.CharacterRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
@AllArgsConstructor
public class AsterixController {

    private final CharacterRepository repo;

    @GetMapping
    public List<AsterixCharacter> getAllCharacters(){
        return repo.findAll();
    }

    @GetMapping("/search")
    public List<AsterixCharacter> getAllCharacters(@RequestParam String q){
        return repo.findAsterixCharacterByProfessionContaining(q);
    }
}
