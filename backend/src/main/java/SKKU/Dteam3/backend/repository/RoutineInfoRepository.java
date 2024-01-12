package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public List<TownRoutineInfo> findAllTownRoutines() {
        Query query = em.createQuery("select distinct(m), t  from RoutineInfo m join Todo t on t.routineInfo = m where m.town != null and m.endDate >= :endDate order by m.town.id")
                .setParameter("endDate", LocalDate.now());
        List<Object> resultList = query.getResultList();
        return resultList.stream().map(o -> {
            Object[] objects = (Object[]) o;
            return new TownRoutineInfo((RoutineInfo) objects[0], (Todo) objects[1]);
        }).toList();
    }

    public List<PersonalRoutineInfo> findAllPersonalRoutines() {
        Query query = em.createQuery("select distinct(m), t from RoutineInfo m join Todo t on t.routineInfo = m where m.town = null and m.endDate >= :endDate")
                .setParameter("endDate", LocalDate.now());
        List<Object> resultList = query.getResultList();
        return resultList.stream().map(o -> {
            Object[] objects = (Object[]) o;
            return new PersonalRoutineInfo((RoutineInfo) objects[0], (Todo) objects[1]);
        }).toList();
    }
}
