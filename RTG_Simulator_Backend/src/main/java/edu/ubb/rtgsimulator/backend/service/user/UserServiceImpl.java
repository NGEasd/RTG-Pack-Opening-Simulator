package edu.ubb.rtgsimulator.backend.service.user;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import edu.ubb.rtgsimulator.backend.repository.UserRepository;
import edu.ubb.rtgsimulator.backend.service.exception.InvalidPasswordException;
import edu.ubb.rtgsimulator.backend.service.exception.UserNotFoundException;
import edu.ubb.rtgsimulator.backend.repository.exception.UsernameAlreadyExistsException;
import edu.ubb.rtgsimulator.backend.utils.PasswordManager;
import edu.ubb.rtgsimulator.backend.utils.PropertyProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final PropertyProvider provider;
    private final UserRepository userRepo;

    @Override
    public User register(User user) {
        // check if user exists in repo
        // exception will propagate upwards
        String salt = user.getUuid();
        user.setPassword(PasswordManager.generateHashPassword(user.getPassword(), salt));
        userRepo.createDefaultUser(user);
        return user;
    }

    @Override
    public User login(User user) {
        User dbUser = userRepo.getByUsername(user.getUsername());
        if (dbUser == null) {
            throw new UserNotFoundException(user.getUsername());
        }
        String salt = dbUser.getUuid();
        user.setPassword(PasswordManager.generateHashPassword(user.getPassword(), salt));
        if (! dbUser.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException(user.getUsername());
        }
        return dbUser;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.getByUsername(username);
    }

    @Override
    public User getUserByID(Long ID) {
        return userRepo.getById(ID);
    }

    @Override
    public void updateUser(User user) {
        userRepo.update(user);
    }

    @Override
    public void sellPlayer(User user, PlayerCard playerCard) {
        int price = playerCard.getPrice();

        // exceptions will propagate upwards to the controller
        userRepo.deleteCardFromInventory(user, playerCard);
        user.setBalance(user.getBalance() + price);
        userRepo.update(user);
    }

    @Override
    public void addPlayerToInventory(User user, PlayerCard playerCard) {
        userRepo.addCardToInventory(user, playerCard);
    }

    @Override
    public List<PlayerCard> getUsersInventoryPlayers(Long userID) {
        User user = userRepo.getById(userID);
        return userRepo.getInventoryPlayers(user);
    }


}
