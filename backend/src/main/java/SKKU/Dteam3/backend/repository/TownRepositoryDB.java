package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.TownMember;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TownRepositoryDB implements TownRepository{

    private final EntityManager em;

    @Override
    public void save(Town town) {
        em.persist(town);
    }

    @Override
    public void update(Town town) {
        em.merge(town);
    }

    @Override
    public Optional<Town> findByTownId(Long id) {
        Town town = em.find(Town.class, id);
        return Optional.ofNullable(town);
    }

    @Override
    public List<Town> findByUserId(Long id) {
        return em.createQuery("select m from Town m join TownMember t on m.id = t.town.id where t.user.id = :userId", Town.class)
                .setParameter("userId", id).getResultList();
    }

    @Override
    public Optional<Town> findByTownName(String name) {
        try {
            Town town = em.createQuery("select m from Town m where m.name = :name", Town.class)
                    .setParameter("name", name).getSingleResult();
            return Optional.of(town);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Town> findAll() {
        return em.createQuery("select m from Town m", Town.class).getResultList();
    }
}
