package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryDB implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = em.createQuery("select m from User m where m.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class).getResultList();
    }
}
