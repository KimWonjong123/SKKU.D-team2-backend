package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.RoutineInfo;
import SKKU.Dteam3.backend.domain.Town;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoutineInfoRepository {

    private final EntityManager em;

    public void save(RoutineInfo routineInfo) {
        em.persist(routineInfo);
    }

    public RoutineInfo findById(Long id) {
        return em.find(RoutineInfo.class, id);
    }

    public void delete(RoutineInfo routineInfo) {
        em.remove(routineInfo);
    }

    public void update(RoutineInfo routineInfo) {
        em.merge(routineInfo);
    }

    public List<RoutineInfo> findByTown(Town town) {
        return em.createQuery("select m from RoutineInfo m where m.town = :town", RoutineInfo.class)
                .setParameter("town", town).getResultList();
    }
}
