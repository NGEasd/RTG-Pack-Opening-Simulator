package edu.ubb.rtgsimulator.backend.service.user;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.SimpleCardResponseDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.user.User;

import java.util.List;

public interface UserService {
    User register(User user);
    User login(User user);
    User getUserByUsername(String username);
    User getUserByID(Long ID);
    void updateUser(User user);
    void sellPlayer(User user, PlayerCard playerCard);
    void addPlayerToInventory(User user, PlayerCard playerCard);
    List<PlayerCard> getUsersInventoryPlayers(Long userID);

}
