package edu.ubb.rtgsimulator.backend.repository;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.user.User;

import java.util.List;

public interface UserRepository {
    User createDefaultUser(User user);
    User getById(Long id);
    User getByUsername(String name);
    void update(User user);
    void delete(User user);

    void addCardToInventory(User user, PlayerCard card);
    void deleteCardFromInventory(User user, PlayerCard card);

    List<PlayerCard> getInventoryPlayers(User user);
}
