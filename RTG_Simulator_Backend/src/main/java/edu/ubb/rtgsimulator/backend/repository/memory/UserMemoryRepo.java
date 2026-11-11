package edu.ubb.rtgsimulator.backend.repository.memory;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.user.Role;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import edu.ubb.rtgsimulator.backend.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("MEMORY")
@Lazy
public class UserMemoryRepo implements UserRepository {

    private final ConcurrentHashMap<Long, User> users;
    private final AtomicLong idGenerator;

    public UserMemoryRepo() {
        users = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong();
    }

    @Override
    public User createDefaultUser(User user) {
        user.setUserID(idGenerator.incrementAndGet());
        user.setRole(Role.DEFAULT);
        user.setBalance(0);
        users.put(user.getUserID(), user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }

    @Override
    public User getByUsername(String name) {
        for (User user : users.values()) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) {
        users.put(user.getUserID(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getUserID());
    }

    private boolean usernameExists(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addCardToInventory(User user, PlayerCard card) {

    }

    @Override
    public void deleteCardFromInventory(User user, PlayerCard card) {

    }

    @Override
    public List<PlayerCard> getInventoryPlayers(User user) {
        return null;
    }
}
