package org.example.asterixapi.controller;

import org.example.asterixapi.model.AsterixCharacter;
import org.example.asterixapi.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AsterixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository repo;

    @Test
    void getAllCharacters_shouldReturnListWithTwoAsterixCharakters_whenTwoExist() throws Exception {
        //GIVEN
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "John", 55, "Dev");
        repo.save(asterixCharacter);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [   {
                                 "id": "1",
                                 "name": "John",
                                 "age": 55,
                                 "profession": "Dev"
                             }]
                        """));

    }

    @Test
    void getAsterixCharacterById_shouldReturnOneCharacter_whenThisExist() throws Exception {
        //GIVEN
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "John", 55, "Dev");
        repo.save(asterixCharacter);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/1"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                                                         "id": "1",
                                                         "name": "John",
                                                         "age": 55,
                                                         "profession": "Dev"
                                                     }
                        """));
    }

    @Test
    void getAsterixCharacterById_shouldReturnOneCharacter_whenManyIncludingThisExist() throws Exception {
        //GIVEN
        AsterixCharacter asterixCharacter_one = new AsterixCharacter("1", "John", 55, "Dev");
        AsterixCharacter asterixCharacter_two = new AsterixCharacter("2", "Bob", 25, "QA");
        AsterixCharacter asterixCharacter_three = new AsterixCharacter("3", "Ben", 45, "Dev");

        repo.saveAll(List.of(asterixCharacter_one, asterixCharacter_two, asterixCharacter_three));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/2"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                                                         "id": "2",
                                                         "name": "Bob",
                                                         "age": 25,
                                                         "profession": "QA"
                                                     }
                        """));
    }

    @Test
    void getAsterixCharacterById_shouldReturnNull_whenManyIncludingThisNotExist() throws Exception {
        //GIVEN
        AsterixCharacter asterixCharacter_one = new AsterixCharacter("1", "John", 55, "Dev");
        AsterixCharacter asterixCharacter_three = new AsterixCharacter("3", "Ben", 45, "Dev");

        repo.saveAll(List.of(asterixCharacter_one, asterixCharacter_three));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/2"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void createAsterixCharacter() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                 "name": "Bob",
                                 "age": 25,
                                 "profession": "QA"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                          "name": "Bob",
                          "age": 25,
                          "profession": "QA"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

    }
}