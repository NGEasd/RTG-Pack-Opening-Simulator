package edu.ubb.rtgsimulator.backend.controller;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.*;
import edu.ubb.rtgsimulator.backend.controller.mapper.CardMapper;
import edu.ubb.rtgsimulator.backend.service.card.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PlayerCardController {
    final CardService cardService;
    final CardMapper mapper;

    // akarki
    // geten keresztul ugye query paramkent kapja
    @GetMapping("/api/players")
    public List<SimpleCardResponseDTO> getCards(CardReqParamsDTO params){
        return cardService.getFilteredCards(params).stream().map(card -> mapper.toSimpleCardDto(card)).toList();
    }

    // akarki
    @GetMapping("/api/players/{id}")
    public DetailedCardResponseDTO getCardById(@PathVariable Long id) {
        var card = cardService.getCardById(id);
        return mapper.toDetailedCardDto(card);
    }

    // rakenyszeritjuk a validalast: a reqbody JSON-je konvertalodik DTO-va
    // es mivel van valid a dto-n, lesz egy validalas is rajta
    // CSAK ADMIN
    @PostMapping("/api/players/add")
    public void addCardToSystem(@RequestBody @Valid CardRequestDTO inputCard) {
        System.out.println(inputCard.toString());
        cardService.addCard(mapper.toEntity(inputCard));
    }

    @PutMapping("/api/players/add")
    public void updateCard(@RequestBody @Valid UpdatedCardDTO card) {
        cardService.updateCard(mapper.toUpdatedCard(card));
    }
}
