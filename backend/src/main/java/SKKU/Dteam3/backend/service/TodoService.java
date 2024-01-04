package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.Repository.ResultRepository;
import SKKU.Dteam3.backend.Repository.RoutineInfoRepository;
import SKKU.Dteam3.backend.Repository.TodoRepository;
import SKKU.Dteam3.backend.domain.Result;
import SKKU.Dteam3.backend.domain.RoutineInfo;
import SKKU.Dteam3.backend.domain.Todo;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.AddTodoRequestDto;
import SKKU.Dteam3.backend.dto.AddTodoResponseDto;
import SKKU.Dteam3.backend.dto.CheckTodoResponseDto;
import SKKU.Dteam3.backend.dto.UncheckTodoResponseDto;
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
            try {
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
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("루틴의 상세정보가 누락되었습니다.");
            }
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
        Result result = new Result(
                user,
                todo
        );
        resultRepository.save(result);
        return new AddTodoResponseDto(todo.getId(), todo.getCreatedAt());
    }

    public CheckTodoResponseDto checkTodo(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo가 없습니다.")
        );
        checkPermission(todo, user);
        Result result = resultRepository.finyByTodo(
                todo
        ).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo에 해당하는 Result가 없습니다.")
        );
        result.check();
        return new CheckTodoResponseDto(todo.getId());
    }

    public UncheckTodoResponseDto uncheckTodo (Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo가 없습니다.")
        );
        checkPermission(todo, user);
        if (!user.getId().equals(todo.getUser().getId())) {
            throw new IllegalArgumentException("해당 Todo에 대한 권한이 없습니다.");
        }
        Result result = resultRepository.finyByTodo(
                todo
        ).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo에 해당하는 Result가 없습니다.")
        );
        result.uncheck();
        return new UncheckTodoResponseDto(todo.getId());
    }

    private void checkPermission(Todo todo, User user)
    {
        if (!user.getId().equals(todo.getUser().getId())) {
            throw new IllegalArgumentException("해당 Todo에 대한 권한이 없습니다.");
        }
    }
}
