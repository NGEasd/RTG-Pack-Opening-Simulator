package edu.ubb.rtgsimulator.backend.controller.mapper;

import edu.ubb.rtgsimulator.backend.controller.dto.PackResponseDTO;
import edu.ubb.rtgsimulator.backend.modell.pack.Pack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackMapper {
    PackResponseDTO toPackResponseDto(Pack pack);
}
