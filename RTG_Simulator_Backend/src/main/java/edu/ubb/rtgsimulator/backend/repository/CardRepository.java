package edu.ubb.rtgsimulator.backend.repository;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.CardReqParamsDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;

import java.util.List;

public interface CardRepository {
    PlayerCard create(PlayerCard card);
    PlayerCard getById(Long id);
    void update(PlayerCard card);
    void delete(PlayerCard card);
    List<PlayerCard> getAllCards();
    List<PlayerCard> getFilteredCards(CardReqParamsDTO params);
}
