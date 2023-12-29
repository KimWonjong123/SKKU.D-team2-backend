package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface TownRepository {

    public Town findBytownId(Long id);
    public List<Town> findByuserId(Long id);
    public List<Town> findAll();

    public Town save(Town town);

}
