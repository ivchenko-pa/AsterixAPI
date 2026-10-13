package org.example.asterixapi.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder
@With
public record AsterixCharacter(String id, String  name, int age, String  profession) {
}
