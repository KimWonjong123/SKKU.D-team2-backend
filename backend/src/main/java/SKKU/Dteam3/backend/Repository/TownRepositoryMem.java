package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownRepositoryMem implements TownRepository {
    private static final Map<Long, Town> store = new HashMap<>();

    private static long sequence = 0L;

    public Town findBytownId(Long id) {return store.get(id);}

    public List<Town> findByuserId(Long id) {return new ArrayList<>(store.values());} //jsql 필요
    public List<Town> findAll(){
        return new ArrayList<>(store.values());
    }

    public Town save(Town town){
        store.put(town.getId(), town);
        return town;
    }
}
