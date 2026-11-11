package edu.ubb.rtgsimulator.backend.controller.dto.users;

import edu.ubb.rtgsimulator.backend.modell.user.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userID;
    private String username;
    private Role role;
    private int balance;
}
