package edu.ubb.rtgsimulator.backend.repository.memory;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.CardReqParamsDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.repository.CardRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("MEMORY")
@Lazy
public class CardMemoryRepo implements CardRepository {
    private final ConcurrentHashMap<Long, PlayerCard> cards;
    private final AtomicLong idGenerator;

    public CardMemoryRepo(){
        cards = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong();
    }

    @Override
    public PlayerCard create(PlayerCard card) {
        card.setPlayerID(idGenerator.incrementAndGet());
        cards.put(card.getPlayerID(), card);
        return card;
    }

    @Override
    public PlayerCard getById(Long id) {
        return cards.get(id);
    }

    @Override
    public void update(PlayerCard card) {
        cards.put(card.getPlayerID(), card);
    }

    @Override
    public void delete(PlayerCard card) {
        cards.remove(card.getPlayerID());
    }

    @Override
    public List<PlayerCard> getAllCards() {
        return new ArrayList<PlayerCard>(cards.values());
    }

    @Override
    public List<PlayerCard> getFilteredCards(CardReqParamsDTO params) {
        return null;
    }
}
