package edu.ubb.rtgsimulator.backend.repository;

import edu.ubb.rtgsimulator.backend.modell.pack.Pack;

import java.util.List;

public interface PackRepository {
    Pack createPack(Pack pack);
    Pack getById(Long id);
    void updatePack(Pack pack);
    List<Pack> getAllPacks();
}
