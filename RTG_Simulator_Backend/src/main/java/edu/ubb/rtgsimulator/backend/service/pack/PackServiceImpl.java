package edu.ubb.rtgsimulator.backend.service.pack;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.pack.Pack;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import edu.ubb.rtgsimulator.backend.repository.CardRepository;
import edu.ubb.rtgsimulator.backend.repository.PackRepository;
import edu.ubb.rtgsimulator.backend.service.exception.NotEnoughMoneyException;
import edu.ubb.rtgsimulator.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepo;
    private final CardRepository cardRepo;
    private final UserService userService;
    private final Random randomGenerator = new Random();
    @Override
    public List<Pack> getAllPacks() {
        return packRepo.getAllPacks();
    }

    @Override
    public PlayerCard openPack(Long packID, Long userID) {

        Pack openingPack = packRepo.getById(packID);
        User user = userService.getUserByID(userID);

        // have enough money
        if (user.getBalance() >= openingPack.getPrice()) {

            PlayerCard openedCard;
            switch (openingPack.getName()) {
                case "Standard Pack": {
                    int maxSize = cardRepo.getAllCards().size();
                    int randIdx = randomGenerator.nextInt(maxSize);

                    openedCard = cardRepo.getAllCards().get(randIdx);
                    break;
                }

                case "Pro Pack", "Ultimate Pack" : {
                    List<PlayerCard> eligiblePlayers = cardRepo.getAllCards().stream()
                            .filter(card -> card.getOverall() >= openingPack.getMinimumOverall())
                            .toList();

                    int maxSize = eligiblePlayers.size();
                    int randIdx = randomGenerator.nextInt(maxSize);

                    openedCard = eligiblePlayers.get(randIdx);
                    break;
                }

                // TODO: valami jo exception erre a lehetetlen esetre
                default:
                    throw new NotEnoughMoneyException();
            }

            // update user balance, after successful purchase
            user.setBalance(user.getBalance() - openingPack.getPrice());
            userService.updateUser(user);
            userService.addPlayerToInventory(user, openedCard);
            System.out.println(openedCard);

            return openedCard;
        } else {
            throw new NotEnoughMoneyException();
        }

    }

    @Override
    public void addPack(Pack pack) {
        packRepo.createPack(pack);
    }
}
