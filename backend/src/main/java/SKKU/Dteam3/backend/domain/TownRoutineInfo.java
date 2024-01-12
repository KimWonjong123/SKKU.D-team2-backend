package SKKU.Dteam3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TownRoutineInfo {
    private String content;
    private String todoClass;
    private RoutineInfo routineInfo;
    private Town town;

    public TownRoutineInfo(RoutineInfo routineInfo, Todo todo) {
        this.routineInfo = routineInfo;
        this.content = todo.getContent();
        this.todoClass = todo.getTodoClass();
        this.town = routineInfo.getTown();
    }
}
