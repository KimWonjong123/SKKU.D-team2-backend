package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Todo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final EntityManager em;

    public void save(Todo todo) {
        em.persist(todo);
    }

    public Optional<Todo> findById(Long id) {
        Todo todo = em.find(Todo.class, id);
        return Optional.ofNullable(todo);
    }

    public void delete(Todo todo) {
        em.remove(todo);
    }

    public void update(Todo todo) {
        em.merge(todo);
    }
}
