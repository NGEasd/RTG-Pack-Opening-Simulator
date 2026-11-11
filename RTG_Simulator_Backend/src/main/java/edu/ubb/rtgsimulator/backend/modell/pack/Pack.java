package edu.ubb.rtgsimulator.backend.modell.pack;

import lombok.Data;

@Data
public class Pack {
    private Long packID;
    private String name;
    private String description;
    private int minimumOverall;
    private int price;
}
