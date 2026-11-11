package edu.ubb.rtgsimulator.backend.controller.mapper;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.CardRequestDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.cards.DetailedCardResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.cards.SimpleCardResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.cards.UpdatedCardDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    SimpleCardResponseDTO toSimpleCardDto(PlayerCard card);
    DetailedCardResponseDTO toDetailedCardDto(PlayerCard card);
    PlayerCard toEntity(CardRequestDTO inputCard);
    PlayerCard toUpdatedCard(UpdatedCardDTO card);
}
