package edu.ubb.rtgsimulator.backend.controller.dto.cards;

import edu.ubb.rtgsimulator.backend.modell.card.Position;
import edu.ubb.rtgsimulator.backend.modell.card.Rarity;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CardReqParamsDTO {
    @Nullable
    @Min(40) @Max(99)
    Integer minimOVR;

    @Nullable
    @Min(40) @Max(99)
    Integer maxOVR;

    @Nullable
    Rarity rarity;

    @Nullable
    Position position;

    @Nullable
    Boolean sortByAcc;
}
