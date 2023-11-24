package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TownRepository {

    private final EntityManager em;



    public Town findByTownId(Long id) {return em.find(Town.class,id);}

    public List<Town> findByUserId(Long id) {
        return em.createQuery("select tm from TownMember tm inner join tm.town t inner join tm.user u where u.id = :id",Town.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Long saveTown(Town town){
        em.persist(town);
        return town.getId();
    }
}
