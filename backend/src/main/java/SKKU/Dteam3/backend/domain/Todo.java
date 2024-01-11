package SKKU.Dteam3.backend.domain;

import SKKU.Dteam3.backend.dto.AddTodoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private String content;

    @NotNull
    private String todoClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_info_id")
    private RoutineInfo routineInfo;

    public Todo(String content, String todoClass, User user, RoutineInfo routineInfo) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.todoClass = todoClass;
        this.user = user;
        this.routineInfo = routineInfo;
    }

    public void modifyTodo(AddTodoRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
