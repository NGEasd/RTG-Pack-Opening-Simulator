package edu.ubb.rtgsimulator.backend.controller.mapper;

import edu.ubb.rtgsimulator.backend.controller.dto.users.UserResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.users.UserValidatorDTO;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserValidatorDTO inputUser);
    UserResponseDTO toResponseDTO(User user);
}
