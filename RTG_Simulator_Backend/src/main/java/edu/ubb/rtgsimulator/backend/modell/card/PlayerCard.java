package edu.ubb.rtgsimulator.backend.modell.card;

import lombok.Data;

@Data
public class PlayerCard {
    private Long playerID;
    private String name;
    private Position position;
    private Rarity rarity;
    private Stat stats;
    private int overall;
    private int price;
}
