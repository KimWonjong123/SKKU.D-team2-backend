package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.TownMember;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TownMemberRepository {

    private final EntityManager em;

    public void save(TownMember townMember) {
        em.persist(townMember);
    }

    public void update(TownMember townMember) {
        em.merge(townMember);
    }

    public void delete(TownMember townMember) {
        em.remove(townMember);
    }

    public Optional<TownMember> findById(Long id) {
        TownMember townMember = em.find(TownMember.class, id);
        return Optional.ofNullable(townMember);
    }

    public List<TownMember> findByUserId(Long id) {
        return em.createQuery("select m from TownMember m where m.user.id = :id", TownMember.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<TownMember> findByTownId(Long id) {
        return em.createQuery("select m from TownMember m where m.town.id = :id", TownMember.class)
                .setParameter("id", id)
                .getResultList();
    }


}
