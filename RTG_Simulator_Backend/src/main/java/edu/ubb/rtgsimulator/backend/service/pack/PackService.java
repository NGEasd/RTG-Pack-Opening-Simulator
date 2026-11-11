package edu.ubb.rtgsimulator.backend.service.pack;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.pack.Pack;

import java.util.List;

public interface PackService {
    List<Pack> getAllPacks();
    PlayerCard openPack(Long packID, Long userID);
    void addPack(Pack pack);
}
