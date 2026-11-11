package edu.ubb.rtgsimulator.backend.controller.dto.cards;

import edu.ubb.rtgsimulator.backend.modell.card.Position;
import edu.ubb.rtgsimulator.backend.modell.card.Rarity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CardRequestDTO {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+")
    @Size(min = 3, max = 50)
    String name;

    @NotNull
    Position position;

    @NotNull
    Rarity rarity;

    @NotNull
    @Valid
    CardStatDTO stats;

    @NotNull
    @Min(40)
    @Max(99)
    int overall;

    @NotNull
    @Min(0)
    int price;
}
