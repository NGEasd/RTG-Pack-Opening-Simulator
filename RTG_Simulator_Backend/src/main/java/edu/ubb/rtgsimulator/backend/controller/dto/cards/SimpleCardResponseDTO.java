package edu.ubb.rtgsimulator.backend.controller.dto.cards;

import edu.ubb.rtgsimulator.backend.modell.card.Position;
import edu.ubb.rtgsimulator.backend.modell.card.Rarity;
import lombok.Data;

@Data
public class SimpleCardResponseDTO {
    private Long playerID;
    private String name;
    private Position position;
    private Rarity rarity;
    private int overall;
    private int price;
}
