package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Guestbook;
import SKKU.Dteam3.backend.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GuestbookRepository {
    private final EntityManager em;

    public void save(Guestbook guestbook) {
        em.persist(guestbook);
    }

    public void update(Guestbook guestbook) {
        em.merge(guestbook);
    }

    public void delete(Guestbook guestbook) {
        em.remove(guestbook);
    }

    public List<Guestbook> findByUserAndDate(User user, LocalDate date) {
        return em.createQuery("select g from Guestbook g where g.user = :user and g.date between :date and :tomorrow", Guestbook.class)
                .setParameter("user", user)
                .setParameter("date", date)
                .setParameter("tomorrow", date.plusDays(1))
                .getResultList();
    }

    public Optional<Guestbook> findByWriter(User writer) {
        try {
            Guestbook guestbook = em.createQuery(
                    "select g " +
                            "from Guestbook g " +
                            "where g.user = :writer and g.date = :date", Guestbook.class)
                    .setParameter("writer", writer)
                    .setParameter("date", LocalDate.now())
                    .getSingleResult();
            return Optional.of(guestbook);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
