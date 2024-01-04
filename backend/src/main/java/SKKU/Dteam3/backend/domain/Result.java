package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean isDone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    @NotNull
    private Todo todo;

    public Result(User user, Todo todo) {
        this.isDone = false;
        this.user = user;
        this.todo = todo;
    }

    public void check() {
        this.isDone = true;
    }

    public void uncheck() {
        this.isDone = false;
    }
}
