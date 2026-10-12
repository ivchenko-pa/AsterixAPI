package org.example.asterixapi.model;

import org.springframework.data.annotation.Id;

public record AsterixCharacter(String id, String  name, int age, String  profession) {
}
