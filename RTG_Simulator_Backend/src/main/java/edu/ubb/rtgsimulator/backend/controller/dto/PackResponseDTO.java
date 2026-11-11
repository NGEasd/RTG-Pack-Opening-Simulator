package edu.ubb.rtgsimulator.backend.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PackResponseDTO {
    Long packID;
    String name;
    String description;
    int price;
}
