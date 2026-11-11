package edu.ubb.rtgsimulator.backend.controller;

import edu.ubb.rtgsimulator.backend.controller.dto.PackResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.cards.SimpleCardResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.mapper.CardMapper;
import edu.ubb.rtgsimulator.backend.controller.mapper.PackMapper;
import edu.ubb.rtgsimulator.backend.service.exception.UserNotFoundException;
import edu.ubb.rtgsimulator.backend.service.pack.PackService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PackController {
    private final PackService packService;
    private final PackMapper packMapper;
    private final CardMapper cardMapper;

    @GetMapping("/api/packs")
    public List<PackResponseDTO>getPacks() {
        return packService.getAllPacks().stream().map(packMapper::toPackResponseDto).collect(Collectors.toList());
    }

    @GetMapping("/api/packs/{id}")
    public SimpleCardResponseDTO openPack(@PathVariable Long id, HttpServletRequest req){
        // get user to balance update
        try {
            Long userID = Long.parseLong((String)req.getAttribute("userID"));
            return cardMapper.toSimpleCardDto(packService.openPack(id, userID));
        } catch (NumberFormatException e) {
            throw new UserNotFoundException("unvalid ID");
        }

    }
}
