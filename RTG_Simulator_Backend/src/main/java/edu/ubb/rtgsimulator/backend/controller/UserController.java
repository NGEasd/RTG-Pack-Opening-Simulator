package edu.ubb.rtgsimulator.backend.controller;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.SimpleCardResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.users.LoginUserResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.users.UserResponseDTO;
import edu.ubb.rtgsimulator.backend.controller.dto.users.UserValidatorDTO;
import edu.ubb.rtgsimulator.backend.controller.mapper.CardMapper;
import edu.ubb.rtgsimulator.backend.controller.mapper.UserMapper;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import edu.ubb.rtgsimulator.backend.service.auth.AuthenticationService;
import edu.ubb.rtgsimulator.backend.service.card.CardService;
import edu.ubb.rtgsimulator.backend.service.exception.UserNotFoundException;
import edu.ubb.rtgsimulator.backend.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    final AuthenticationService authService;
    final UserService userService;
    final CardService cardService;
    final UserMapper userMapper;
    final CardMapper cardMapper;

    // akarki
    @PostMapping("/api/users/register")
    public void registerUser(@RequestBody @Valid UserValidatorDTO userDTO) {
        userService.register(userMapper.toEntity(userDTO));
    }

    // akarki
    @PostMapping("/api/users/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid UserValidatorDTO userDTO) {
        User loginUser = userService.login(userMapper.toEntity(userDTO));

        // generate jjwt
        String jwtToken = authService.generateToken(loginUser.getUserID().toString());

        // response builder
        LoginUserResponseDTO responseDTO = new LoginUserResponseDTO();
        responseDTO.setJwtToken(jwtToken);
        responseDTO.setUserRespDTO(userMapper.toResponseDTO(loginUser));

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // ilyen szinten nem is kell
    // nem is hasznalja sehol vegul, mert ez a regi valtozat
    @PostMapping("/api/users/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // ne hozzon letre ujat, csak lekerjen
        if (session != null) {
            session.invalidate();
            return new ResponseEntity<>("Logout success!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No active logout.", HttpStatus.OK);
        }
    }

    // CSAK USER!
    @GetMapping("/api/users/inventory")
    public List<SimpleCardResponseDTO> getInventoryContent(HttpServletRequest request) {
        try {
            Long userID = Long.parseLong((String)request.getAttribute("userID"));
            return userService.getUsersInventoryPlayers(userID)
                    .stream()
                    .map(cardMapper::toSimpleCardDto).toList();
        } catch (NumberFormatException e) {
            throw new UserNotFoundException("unvalid ID");
        }
    }

    // CSAK USER
    @DeleteMapping("/api/users/inventory/{id}")
    public void sellCard(HttpServletRequest request, @PathVariable Long id) {
        try {
            Long userID = Long.parseLong((String)request.getAttribute("userID"));
            User user = userService.getUserByID(userID);
            System.out.println(user);
            PlayerCard card = cardService.getCardById(id);
            System.out.println(card);
            userService.sellPlayer(user, card);
        } catch (NumberFormatException e) {
            throw new UserNotFoundException("unvalid ID");
        }
    }

    // CSAK USER
    @GetMapping("/api/users/me")
    public UserResponseDTO getCurrentUser(HttpServletRequest req) {
        try {
            Long userID = Long.parseLong((String)req.getAttribute("userID"));
            System.out.println((String)req.getAttribute("userID"));
            User user = userService.getUserByID(userID);
            return userMapper.toResponseDTO(user);
        } catch (NumberFormatException e) {
            System.out.println((String)req.getAttribute("userID"));
            throw new UserNotFoundException("unvalid ID");
        }
    }

}
