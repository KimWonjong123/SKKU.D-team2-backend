package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Town;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TownRepositoryMem implements TownRepository {

    private static final Map<Long, Town> store = new HashMap<>();

    private static long sequence = 0L;

    @Override
    public Optional<Town> findByTownId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Town> findByUserId(Long id) {return new ArrayList<>(store.values());} //jsql 필요

    @Override
    public Optional<Town> findByTownName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Town> findAll(){
        return new ArrayList<>(store.values());
    }

    @Override
    public void save(Town town){
        store.put(town.getId(), town);
    }

    @Override
    public void update(Town town) {

    }
}
