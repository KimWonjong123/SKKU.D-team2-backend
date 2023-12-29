package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.Repository.ResultRepository;
import SKKU.Dteam3.backend.Repository.RoutineInfoRepository;
import SKKU.Dteam3.backend.Repository.TodoRepository;
import SKKU.Dteam3.backend.domain.RoutineInfo;
import SKKU.Dteam3.backend.domain.Todo;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.AddTodoRequestDto;
import SKKU.Dteam3.backend.dto.AddTodoResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    private final ResultRepository resultRepository;

    private final RoutineInfoRepository routineInfoRepository;

    public AddTodoResponseDto addTodo(AddTodoRequestDto requestDto, User user) {
        Todo todo = null;
        if (requestDto.getRoutine()) {
            // 루틴일 경우
            RoutineInfo routineInfo = new RoutineInfo(
                    LocalDate.now(),
                    requestDto.getEndDate().toLocalDate(),
                    requestDto.getMon(),
                    requestDto.getTue(),
                    requestDto.getWed(),
                    requestDto.getThu(),
                    requestDto.getFri(),
                    requestDto.getSat(),
                    requestDto.getSun()
            );
            routineInfoRepository.save(routineInfo);
            todo = new Todo(
                    requestDto.getContent(),
                    requestDto.getTodoClass(),
                    user,
                    routineInfo
            );
            todoRepository.save(todo);
        } else {
            // 루틴이 아닐 경우
            todo = new Todo(
                    requestDto.getContent(),
                    requestDto.getTodoClass(),
                    user,
                    null
            );
            todoRepository.save(todo);
        }
        return new AddTodoResponseDto(todo.getId(), todo.getCreatedAt());
    }
}
