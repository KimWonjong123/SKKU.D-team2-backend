package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Todo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final EntityManager em;

    public void save(Todo todo) {
        em.persist(todo);
    }

    public Todo findById(Long id) {
        return em.find(Todo.class, id);
    }

    public void delete(Todo todo) {
        em.remove(todo);
    }

    public void update(Todo todo) {
        em.merge(todo);
    }
}
