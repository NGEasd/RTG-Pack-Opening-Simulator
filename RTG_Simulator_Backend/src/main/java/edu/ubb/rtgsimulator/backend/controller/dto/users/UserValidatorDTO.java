package edu.ubb.rtgsimulator.backend.controller.dto.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserValidatorDTO {
    @NotNull
    @Size(min=4, max=20)
    String username;

    @NotNull
    @Size(min=8, max=64)
    String password;
}
