package edu.ubb.rtgsimulator.backend;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.card.Position;
import edu.ubb.rtgsimulator.backend.modell.card.Rarity;
import edu.ubb.rtgsimulator.backend.modell.card.Stat;
import edu.ubb.rtgsimulator.backend.modell.pack.Pack;
import edu.ubb.rtgsimulator.backend.modell.user.Role;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import edu.ubb.rtgsimulator.backend.repository.UserRepository;
import edu.ubb.rtgsimulator.backend.service.card.CardService;
import edu.ubb.rtgsimulator.backend.service.pack.PackService;
import edu.ubb.rtgsimulator.backend.service.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Main {

    @Autowired
    UserService us;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void initialize() {

        /*
        User user = new User();
        user.setUsername("adminka");
        user.setPassword("komplexPW");
        user.setRole(Role.ADMIN);
        user.getUuid();
        user.setBalance(10000);

        us.register(user);

        /*

        // stat setter
        Stat stat = new Stat();
        stat.setPace(75);
        stat.setDefending(48);
        stat.setDribbling(78);
        stat.setShooting(89);
        stat.setPassing(76);
        stat.setKeeperAbilities(9);

        // rarity setter
        Rarity rarity = Rarity.BASE;


        PlayerCard card1 = new PlayerCard();
        card1.setName("Robert Lewandowski");
        card1.setOverall(90);
        card1.setPrice(500000);
        card1.setPosition(Position.STRIKER);
        card1.setStats(stat);
        card1.setRarity(rarity);

        stat.setPace(85);
        stat.setDribbling(88);
        stat.setShooting(83);
        rarity = Rarity.BRONZE;
        PlayerCard card2 = new PlayerCard();
        card2.setName("Serge Gnabry");
        card2.setOverall(85);
        card2.setPrice(200000);
        card2.setPosition(Position.STRIKER);
        card2.setStats(stat);
        card2.setRarity(rarity);

        stat.setPace(88);
        stat.setDribbling(86);
        stat.setShooting(79);
        rarity = Rarity.SILVER;
        PlayerCard card3 = new PlayerCard();
        card3.setName("Kingsley Coman");
        card3.setOverall(86);
        card3.setPrice(300000);
        card3.setPosition(Position.STRIKER);
        card3.setRarity(rarity);
        card3.setStats(stat);

        stat.setPace(91);
        stat.setDribbling(89);
        stat.setShooting(81);
        rarity = Rarity.GOLD;

        PlayerCard card4 = new PlayerCard();
        stat.setShooting(84);
        stat.setPace(92);
        stat.setDefending(36);
        stat.setPassing(78);
        stat.setDribbling(94);
        stat.setKeeperAbilities(7);
        card4.setName("Osmane Dembelé");
        card4.setOverall(88);
        card4.setPrice(600000);
        card4.setPosition(Position.STRIKER);
        card4.setRarity(rarity);
        card4.setStats(stat);

        cardService.addCard(card1);
        cardService.addCard(card2);
        cardService.addCard(card3);
        cardService.addCard(card4);

        // Packs
        Pack pack = new Pack();
        pack.setName("Base Pack");
        pack.setDescription("Default free pack, always hopeful, never underestimate this!");
        pack.setPrice(0);
        packService.addPack(pack);

        */
    }

}
