package edu.ubb.rtgsimulator.backend.controller.dto.cards;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CardStatDTO {
        @Min(40)
        @Max(99)
        private int pace;

        @Min(40)
        @Max(99)
        private int shooting;

        @Min(40)
        @Max(99)
        private int dribbling;

        @Min(40)
        @Max(99)
        private int passing;

        @Min(40)
        @Max(99)
        private int defending;

        @Min(40)
        @Max(99)
        private int keeperAbilities;
}
