package edu.ubb.rtgsimulator.backend.controller.dto.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserResponseDTO {
    @NotNull
    private String jwtToken;

    @Valid
    private UserResponseDTO userRespDTO;

}
