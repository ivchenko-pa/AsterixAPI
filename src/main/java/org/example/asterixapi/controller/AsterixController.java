package org.example.asterixapi.controller;

import lombok.AllArgsConstructor;
import org.example.asterixapi.dto.AsterixCharacterDto;
import org.example.asterixapi.model.AsterixCharacter;
import org.example.asterixapi.service.AsterixService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
@AllArgsConstructor
public class AsterixController {

    private final AsterixService service;

    @GetMapping
    public List<AsterixCharacter> getAllCharacters(){

        return service.getAllCharacters();
    }

    @GetMapping("/{id}")
    public AsterixCharacter getAsterixCharacterById(@PathVariable String id){
        return service.getAsterixCharacterById(id);
    }

    @PostMapping
    public void createAsterixCharacter(@RequestBody AsterixCharacterDto asterixCharacterDto){
        service.createAsterixCharacter(asterixCharacterDto);
    }

    @PutMapping("/{id}")
    public void updateAsterixCharacter(@PathVariable String id, @RequestBody AsterixCharacterDto asterixCharacterDto){
        service.updateAsterixCharacter(id, asterixCharacterDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAsterixCharacter(@PathVariable String id){
        return service.deleteAsterixCharacter(id);
    }
}
