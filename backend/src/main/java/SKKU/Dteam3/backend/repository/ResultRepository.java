package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.AchievementRate;
import SKKU.Dteam3.backend.domain.Result;
import SKKU.Dteam3.backend.domain.Todo;
import SKKU.Dteam3.backend.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResultRepository {

    private final EntityManager em;

    public void save(Result result) {
        em.persist(result);
    }

    public Optional<Result> findById(Long id) {
        Result result = em.find(Result.class, id);
        return Optional.ofNullable(result);
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

    public Optional<Result> findByTodo(Todo todo) {
        try {
            Result result = em.createQuery("select m from Result m where m.todo = :todo", Result.class)
                    .setParameter("todo", todo).getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Float calculateAchievementRateByUser(User user, LocalDateTime date) {
        return em.createQuery("select (sum(case when r.isDone = true then 1 else 0 end) * 1.0) / count(r) " +
                        "from Result r join Todo t on t.id = r.todo.id where r.user = :user and t.createdAt between :date and :tomorrow", Float.class)
                .setParameter("user", user)
                .setParameter("date", date)
                .setParameter("tomorrow", date.plusDays(1))
                .getSingleResult();
    }

    public List<AchievementRate> calculateMonthAchievementRateByUser(User user, LocalDateTime start, LocalDateTime end) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT DATE(CONVERT_TZ(t.created_at, '+00:00', '+09:00')) AS converted_date, " +
                        "SUM(CASE WHEN r.is_done = 1 THEN 1 ELSE 0 END) * 1.0 / COUNT(*) AS achieved_rate " +
                        "FROM result r JOIN todo t ON r.todo_id = t.id " +
                        "WHERE r.user_id = :userId " +
                        "GROUP BY converted_date " +
                        "ORDER BY converted_date");
//        nativeQuery.setParameter("startDate", start);
//        nativeQuery.setParameter("endDate", end); // TODO: fix date filtering
        nativeQuery.setParameter("userId", user.getId());
        List<Object[]> resultList2 = nativeQuery.getResultList();
        return resultList2.stream().map(objects -> new AchievementRate(
                ((Date) objects[0]).toLocalDate(),
                (int) Math.floor(((BigDecimal) objects[1]).floatValue() * 5) * 20)).toList();
    }
}
