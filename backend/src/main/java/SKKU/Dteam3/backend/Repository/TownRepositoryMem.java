package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;

import java.util.*;

public class TownRepositoryMem implements TownRepository {
    private static final Map<Long, Town> store = new HashMap<>();

    private static long sequence = 0L;

    public Optional<Town> findBytownId(Long id) {return Optional.ofNullable(store.get(id));}

    public List<Town> findByuserId(Long id) {return store.values().stream().toList();} //jsql 필요
    public List<Town> findAll(){return store.values().stream().toList();}

    public Town save(Town town){
        store.put(town.getId(), town);
        return town;
    }
}
