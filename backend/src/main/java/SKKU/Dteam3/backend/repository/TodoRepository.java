package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.*;
import SKKU.Dteam3.backend.dto.AddTodoRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<TodoDetail> findDetailByUserIdAndDate(User user, LocalDate date) {
        Query query = em.createQuery(
                "SELECT t, ro, re " +
                        "FROM Todo t JOIN Result re ON t.id = re.todo.id LEFT JOIN RoutineInfo ro on t.routineInfo.id = ro.id " +
                        "WHERE t.user = :user AND t.createdAt BETWEEN :date AND :tomorrow " +
                        "ORDER BY t.id")
                .setParameter("user", user)
                .setParameter("date", date.atStartOfDay())
                .setParameter("tomorrow", date.plusDays(1).atStartOfDay());
        List<Object> resultList = query.getResultList();
        return resultList.stream().map(o -> {
            Object[] objects = (Object[]) o;
            return new TodoDetail((Todo) objects[0], (RoutineInfo) objects[1], (Result) objects[2]);
        }).toList();
    }

    public List<AddTodoRequestDto> findByTownId(Town town){
                Query query = em.createQuery("SELECT t, ro FROM Todo t LEFT JOIN FETCH RoutineInfo ro on t.routineInfo.id = ro.id WHERE ro.town.id = :id AND t.user.id = :leaderId")
                    .setParameter("id",town.getId())
                    .setParameter("leaderId",town.getLeader().getId());
                List<Object> resultList = query.getResultList();
                        return resultList.stream().map(o -> {
                            Object[] objects = (Object[]) o;
                            return new AddTodoRequestDto(
                                    ((Todo)objects[0]).getContent(),
                                    town.getDescription(),
                                    true,
                                    ((RoutineInfo)objects[1]).getEndDate().atTime(LocalTime.MAX),
                                    ((RoutineInfo)objects[1]).getMon(),
                                    ((RoutineInfo)objects[1]).getTue(),
                                    ((RoutineInfo)objects[1]).getWed(),
                                    ((RoutineInfo)objects[1]).getThu(),
                                    ((RoutineInfo)objects[1]).getFri(),
                                    ((RoutineInfo)objects[1]).getSat(),
                                    ((RoutineInfo)objects[1]).getSun()
                            );
                        }).toList();
    }
}
