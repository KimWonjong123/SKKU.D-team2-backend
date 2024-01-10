package SKKU.Dteam3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDetail {
    private Long id;
    private String content;
    private String todoClass;
    private boolean done;
    private boolean routine;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
    private boolean town;
    private String name;
    private String goal;

    public TodoDetail(Todo todo, RoutineInfo routineInfo, Result resut) {
        this.id  = todo.getId();
        this.content = todo.getContent();
        this.todoClass = todo.getTodoClass();
        this.done = resut.isDone();
        if (routineInfo != null) {
            this.routine = true;
            this.startDate = routineInfo.getStartDate();
            this.endDate = routineInfo.getEndDate();
            this.mon = routineInfo.getMon();
            this.tue = routineInfo.getTue();
            this.wed = routineInfo.getWed();
            this.thu = routineInfo.getThu();
            this.fri = routineInfo.getFri();
            this.sat = routineInfo.getSat();
            this.sun = routineInfo.getSun();
            if (routineInfo.getTown() != null) {
                this.town = true;
                this.name = routineInfo.getTown().getName();
                this.goal = routineInfo.getTown().getDescription();
            } else {
                this.town = false;
            }
        } else {
            this.routine = false;
        }
    }
}
