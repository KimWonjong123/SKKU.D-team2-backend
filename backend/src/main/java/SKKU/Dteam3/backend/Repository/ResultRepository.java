package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Result;
import SKKU.Dteam3.backend.domain.Todo;
import SKKU.Dteam3.backend.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResultRepository {

    private final EntityManager em;

    public void save(Result result) {
        em.persist(result);
    }

    public Result findById(Long id) {
        return em.find(Result.class, id);
    }

    public void delete(Result result) {
        em.remove(result);
    }

    public void update(Result result) {
        em.merge(result);
    }

    public List<Result>  findByUser(User user) {
        return em.createQuery("select m from Result m where m.user = :user", Result.class)
                .setParameter("user", user).getResultList();
    }

    public Result finyByTodo (Todo todo) {
        return em.createQuery("select m from Result m where m.todo = :todo", Result.class)
                .setParameter("todo", todo).getSingleResult();
    }
}
