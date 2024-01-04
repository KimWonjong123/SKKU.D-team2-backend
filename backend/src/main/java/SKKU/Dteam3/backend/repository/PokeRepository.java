package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Poke;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PokeRepository {

    private final EntityManager em;

    public void save(Poke poke) {
        em.persist(poke);
    }

    public void delete(Poke poke) {
        em.remove(poke);
    }

    public List<Poke> findByUserId(Long userId, Long pokeId) {
        return em.createQuery("select m from Poke m where m.user.id = :userId and m.id = :pokeId", Poke.class)
                .setParameter("userId", userId)
                .setParameter("pokeId", pokeId)
                .getResultList();
    }

    public List<Poke> findByPokedUserId(Long userId, Long pokeId) {
        return em.createQuery("select m from Poke m join Todo t where t.user.id = :userId and m.id = :pokeId", Poke.class)
                .setParameter("userId", userId)
                .setParameter("pokeId", pokeId)
                .getResultList();
    }
}
