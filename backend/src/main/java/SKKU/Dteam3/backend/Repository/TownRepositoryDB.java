package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TownRepositoryDB implements TownRepository{

    private final EntityManager em;
    public Optional<Town> findBytownId(Long id) {
        try{
            Town town = em.createQuery("select t from Town t where t.id = :id", Town.class)
                    .setParameter("id",id).getSingleResult();
            return Optional.of(town);
        }
        catch (NoResultException e){
            return Optional.empty();
        }
    }

    public List<Town> findByuserId(Long id) {
        try{
            return em.createQuery("select t from Town t inner join TownMember tm where tm.user.id = :id ", Town.class)
                    .setParameter("id",id).getResultList();
        }
        catch (NoResultException e){
            return new ArrayList<>();
        }
    }
    public List<Town> findAll(){try{
        return em.createQuery("select t from Town t ", Town.class)
                .getResultList();
    }
    catch (NoResultException e){
        return new ArrayList<>();
    }}

    public Town save(Town town){
        store.put(town.getId(), town);
        return town;
    }
}
