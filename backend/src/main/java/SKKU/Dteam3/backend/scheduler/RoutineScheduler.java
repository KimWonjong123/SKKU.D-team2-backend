package SKKU.Dteam3.backend.scheduler;

import SKKU.Dteam3.backend.domain.PersonalRoutineInfo;
import SKKU.Dteam3.backend.domain.Todo;
import SKKU.Dteam3.backend.domain.TownMember;
import SKKU.Dteam3.backend.domain.TownRoutineInfo;
import SKKU.Dteam3.backend.repository.RoutineInfoRepository;
import SKKU.Dteam3.backend.repository.TodoRepository;
import SKKU.Dteam3.backend.repository.TownMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class RoutineScheduler {
    private final RoutineInfoRepository routineInfoRepository;
    private final TownMemberRepository townMemberRepository;
    private final TodoRepository todoRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void createTodoFromRoutine() {
        List<TownRoutineInfo> townRoutines = routineInfoRepository.findAllTownRoutines();
        townRoutines.forEach(r -> {
            List<TownMember> members = townMemberRepository.findByTownId(r.getTown().getId());
            if (!r.getRoutineInfo().getStartDate().equals(r.getRoutineInfo().getEndDate())) {
                members.forEach(m -> {
                    // Todo 생성
                    Todo todo = new Todo(r.getContent(), r.getTodoClass(), m.getUser(), r.getRoutineInfo());
                    // Todo 저장
                    todoRepository.save(todo);
                });
            }
        });

        List<PersonalRoutineInfo> personalRoutines = routineInfoRepository.findAllPersonalRoutines();
        personalRoutines.forEach(r -> {
            Todo todo = new Todo(r.getContent(), r.getTodoClass(), r.getUser(), r.getRoutineInfo());
            todoRepository.save(todo);
        });
    }
}
