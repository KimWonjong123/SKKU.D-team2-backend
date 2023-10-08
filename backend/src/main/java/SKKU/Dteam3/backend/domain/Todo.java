package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String todoClass;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = true)
    private RoutineInfo routineInfo;

    public Todo(String content, LocalDate startDate, LocalDate endDate, String todoClass, User user) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.todoClass = todoClass;
        this.user = user;
        this.routineInfo = null;
    }
}
