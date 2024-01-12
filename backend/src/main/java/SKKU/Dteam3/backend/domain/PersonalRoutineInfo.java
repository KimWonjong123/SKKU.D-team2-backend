package SKKU.Dteam3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRoutineInfo {
    private String content;
    private String todoClass;
    private RoutineInfo routineInfo;
    private User user;

    public PersonalRoutineInfo(RoutineInfo routineInfo, Todo todo) {
        this.routineInfo = routineInfo;
        this.content = todo.getContent();
        this.todoClass = todo.getTodoClass();
        this.user = todo.getUser();
    }
}
