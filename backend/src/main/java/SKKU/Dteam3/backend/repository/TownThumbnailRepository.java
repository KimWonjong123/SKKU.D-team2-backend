package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.TownThumbnail;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TownThumbnailRepository {

    private final EntityManager em;

    public void save(TownThumbnail townThumbnail) {
        em.persist(townThumbnail);
    }

    public void update(TownThumbnail townThumbnail){
        em.merge(townThumbnail);
    }


    public UUID findByTownId(Long id) {
        TownThumbnail townThumbnail = em.createQuery("select tm" +
                " from TownThumbnail tm where tm.town.id = :townId",TownThumbnail.class)
                .setParameter("townId",id).getSingleResult();
        return townThumbnail.getId();
    }
}
