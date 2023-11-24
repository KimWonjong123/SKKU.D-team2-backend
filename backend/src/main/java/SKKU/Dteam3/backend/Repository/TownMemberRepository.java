package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.TownMember;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TownMemberRepository {

    private final EntityManager em;

    public Long saveTownMember(TownMember townMember){
        em.persist(townMember);
        return townMember.getId();
    }

    public TownMember findByTownMemberId(Long id){
        return em.find(TownMember.class, id);
    }
}
