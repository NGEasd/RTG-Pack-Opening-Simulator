package edu.ubb.rtgsimulator.backend.repository.memory;

import edu.ubb.rtgsimulator.backend.modell.pack.Pack;
import edu.ubb.rtgsimulator.backend.repository.PackRepository;
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
public class PackMemoryRepo implements PackRepository {
    private final ConcurrentHashMap<Long, Pack> packs;
    private final AtomicLong idGenerator;

    public PackMemoryRepo(){
        packs = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong();
    }
    @Override
    public Pack createPack(Pack pack) {
        pack.setPackID(idGenerator.incrementAndGet());
        packs.put(pack.getPackID(), pack);
        return pack;
    }

    @Override
    public Pack getById(Long id) {
        return packs.get(id);
    }

    @Override
    public void updatePack(Pack pack) {
        packs.put(pack.getPackID(), pack);
    }

    @Override
    public List<Pack> getAllPacks() {
        return new ArrayList<Pack>(packs.values());
    }
}
