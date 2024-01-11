package SKKU.Dteam3.backend.repository;


import SKKU.Dteam3.backend.domain.Alarm;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmRepository {

    private final EntityManager em;

    public void save(Alarm alarm) {
        em.persist(alarm);
    }

    public void delete(Alarm alarm){
        em.remove(alarm);
    }

    public Integer countAlarm(Long userId){
        return em.createQuery("select m from Alarm m WHERE m.user.id = :userId", Alarm.class)
                .getResultList().size();
    }

    public List<Alarm> findAlarmsByUserId(Long userId){
        return em.createQuery("select m from Alarm m WHERE m.user.id = :userId", Alarm.class)
                .getResultList();
    }
    
    public Alarm findOldestOneByUserId(Long userId){
        return em.createQuery("select m FROM  Alarm m WHERE m.user.id = :userId", Alarm.class)
                .getSingleResult();
    }
}

