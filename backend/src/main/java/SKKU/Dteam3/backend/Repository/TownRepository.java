package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Gathering;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TownRepository {

    private static final Map<Long, Gathering> store = new HashMap<>();

    private static long sequence = 0L;

    public Gathering findBytownId(Long id) {return store.get(id);}

    public List<Gathering> findByuserId(Long id) {return new ArrayList<>(store.values());} //jsql 필요
    public List<Gathering> findAll(){
        return new ArrayList<>(store.values());
    }

    public Gathering save(Gathering gathering){
        gathering.setId(++sequence);
        store.put(gathering.getId(),gathering);
        return gathering;
    }
}
