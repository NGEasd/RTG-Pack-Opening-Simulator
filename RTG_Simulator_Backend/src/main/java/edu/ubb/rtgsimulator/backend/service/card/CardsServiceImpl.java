package edu.ubb.rtgsimulator.backend.service.card;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.CardReqParamsDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.repository.CardRepository;
import edu.ubb.rtgsimulator.backend.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardService {
    private final CardRepository cardRepo;

    @Override
    public PlayerCard addCard(PlayerCard card) {
        return cardRepo.create(card);
    }

    @Override
    public PlayerCard getCardById(Long id) {
        return cardRepo.getById(id);
    }

    @Override
    public List<PlayerCard> getAllCards() {
        return cardRepo.getAllCards();
    }

    @Override
    public List<PlayerCard> getFilteredCards(CardReqParamsDTO params) {
        return cardRepo.getFilteredCards(params);
    }

    @Override
    public void updateCard(PlayerCard card) {
        cardRepo.update(card);
    }

    @Override
    public void removeCard(PlayerCard card) {
        cardRepo.delete(card);
    }
}
