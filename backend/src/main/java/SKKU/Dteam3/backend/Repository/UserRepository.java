package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long saveUser(User user){
        em.persist(user);
        return user.getId();
    }

    public User findByUserId(Long id){
        return em.find(User.class, id);
    }
}
