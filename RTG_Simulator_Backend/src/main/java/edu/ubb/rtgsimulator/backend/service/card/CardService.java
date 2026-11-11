package edu.ubb.rtgsimulator.backend.service.card;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.CardReqParamsDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;

import java.util.List;

public interface CardService {
    PlayerCard addCard(PlayerCard card);
    PlayerCard getCardById(Long id);
    List<PlayerCard> getAllCards();
    List<PlayerCard> getFilteredCards(CardReqParamsDTO params);
    void updateCard(PlayerCard card);
    void removeCard(PlayerCard card);
}
