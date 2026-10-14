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
    public AsterixCharacter createAsterixCharacter(@RequestBody AsterixCharacterDto asterixCharacterDto){
        return service.createAsterixCharacter(asterixCharacterDto);
    }

    @PutMapping("/{id}")
    public AsterixCharacter updateAsterixCharacter(@PathVariable String id, @RequestBody AsterixCharacterDto asterixCharacterDto) throws Exception{
        return service.updateAsterixCharacter(id, asterixCharacterDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAsterixCharacter(@PathVariable String id){
        return service.deleteAsterixCharacter(id);
    }
}
