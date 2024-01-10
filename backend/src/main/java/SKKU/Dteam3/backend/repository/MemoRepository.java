package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Memo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class MemoRepository {

    private final EntityManager em;

    public void save(Memo memo) {
        em.persist(memo);
    }

    public void update(Memo memo) {
        em.merge(memo);
    }

    public Memo findByIdAndDate(Long userId, LocalDate date) throws NoResultException {
        return em.createQuery("select m from Memo m where m.user.id = :userId and m.date = :date", Memo.class)
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();
    }
}
