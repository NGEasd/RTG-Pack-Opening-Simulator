package edu.ubb.rtgsimulator.backend.modell.user;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private Long userID;
    private String username;
    private String password;
    private Role role;
    private List<PlayerCard> inventory;
    private int balance;
}
