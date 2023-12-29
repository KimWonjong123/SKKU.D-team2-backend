package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TownRepository {

    public Optional<Town> findBytownId(Long id);
    public List<Town> findByuserId(Long id);
    public List<Town> findAll();

    public Town save(Town town);

}
