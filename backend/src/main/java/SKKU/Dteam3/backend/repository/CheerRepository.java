package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Cheer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CheerRepository {

    private final EntityManager em;

    public void save(Cheer cheer) {
        em.persist(cheer);
    }

    public void delete(Cheer cheer) {
        em.remove(cheer);
    }

    public Optional<Cheer> findById(Long id) {
        Cheer cheer = em.find(Cheer.class, id);
        return Optional.ofNullable(cheer);
    }

    public Optional<Cheer> findByUserId(Long userId, Long todoId) {
        try {
            Cheer cheer = em.createQuery("select m from Cheer m where m.user.id = :userId and m.todo.id = :todoId", Cheer.class)
                    .setParameter("userId", userId)
                    .setParameter("todoId", todoId)
                    .getSingleResult();
            return Optional.of(cheer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Cheer> findByCheeredUserId(Long userId, Long todoId) {
        try {
            Cheer cheer = em.createQuery("select m from Cheer m join Todo t where t.user.id = :userId and m.todo.id = :todoId", Cheer.class)
                    .setParameter("userId", userId)
                    .setParameter("todoId", todoId)
                    .getSingleResult();
            return Optional.of(cheer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Cheer> findAllByCheeredUserId(Long userId) {
        return em.createQuery("select m from Cheer m join Todo t where t.user.id = :userId", Cheer.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
