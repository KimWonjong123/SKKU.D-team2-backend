package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.repository.ResultRepository;
import SKKU.Dteam3.backend.repository.RoutineInfoRepository;
import SKKU.Dteam3.backend.repository.TodoRepository;
import SKKU.Dteam3.backend.domain.*;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.repository.CheerRepository;
import SKKU.Dteam3.backend.repository.PokeRepository;
import SKKU.Dteam3.backend.repository.TownMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    private final ResultRepository resultRepository;

    private final RoutineInfoRepository routineInfoRepository;

    private final TownMemberRepository townMemberRepository;

    private final PokeRepository pokeRepository;

    private final CheerRepository cheerRepository;

    @Value("${poke.max-count}")
    private static int MAX_POKE;

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
    public AddTodoResponseDto addTownTodo(AddTodoRequestDto requestDto, User user, Town town) {
        Todo todo = null;
        if (requestDto.getRoutine()) {
            // 타운 공통 루틴일 경우
            try {
                RoutineInfo routineInfo = new RoutineInfo(
                        town,
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
            // 루틴이 아닐 경우 -> 개인추가 타운투두이다.
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

    public void saveOrUpdate(AddTodoRequestDto requestDto, User user, Town town) {
        List<Todo> todoList = todoRepository.findTodosByTownId(town.getId(), user.getId());
        for(Todo todo : todoList){
            routineInfoRepository.delete(todo.getRoutineInfo());
            todoRepository.delete(todo);
        }
        try{
            RoutineInfo routineInfo = new RoutineInfo(
                    town,
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
            Todo todo = new Todo(requestDto.getContent(),
                    requestDto.getTodoClass(),
                    user,
                    routineInfo);
            routineInfoRepository.save(todo.getRoutineInfo());
            todoRepository.save(todo);
        }catch (NullPointerException e){
            throw new IllegalArgumentException("루틴 상세 정보가 누락되었습니다.");
        }
    }

    public void saveOrUpdate(List<AddTodoRequestDto> requestDto, User user, Town town) {
        List<Todo> todoList = todoRepository.findTodosByTownId(town.getId(), user.getId());
        for(Todo todo : todoList){
            routineInfoRepository.delete(todo.getRoutineInfo());
            todoRepository.delete(todo);
        }
        for(AddTodoRequestDto dto : requestDto){
            try {
                RoutineInfo routineInfo = new RoutineInfo(
                        town,
                        LocalDate.now(),
                        dto.getEndDate().toLocalDate(),
                        dto.getMon(),
                        dto.getTue(),
                        dto.getWed(),
                        dto.getThu(),
                        dto.getFri(),
                        dto.getSat(),
                        dto.getSun()
                );
                Todo todo = new Todo(dto.getContent(),
                        dto.getTodoClass(),
                        user,
                        routineInfo);
                routineInfoRepository.save(todo.getRoutineInfo());
                todoRepository.save(todo);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("루틴 상세 정보가 누락되었습니다.");
            }
        }
    }

    public List<AddTodoRequestDto> getTownTodo(Town town) {
        return todoRepository.findByTownId(town);
    }

    public void removeTownTodo(Long townId) {
        List<Todo> todoList = todoRepository.findAllTodosByTownId(townId);
        for(Todo todo : todoList){
            deleteTodo(todo.getId(),todo.getUser());
        }
    }

    public CheckTodoResponseDto checkTodo(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo가 없습니다.")
        );
        checkPermission(todo, user);
        validateDate(todo);
        Result result = resultRepository.findByTodo(
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
        validateDate(todo);
        if (!user.getId().equals(todo.getUser().getId())) {
            throw new IllegalArgumentException("해당 Todo에 대한 권한이 없습니다.");
        }
        Result result = resultRepository.findByTodo(
                todo
        ).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo에 해당하는 Result가 없습니다.")
        );
        result.uncheck();
        return new UncheckTodoResponseDto(todo.getId());
    }

    public DeleteTodoResponseDto deleteTodo (Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo가 없습니다.")
        );
        checkPermission(todo, user);
        validateDate(todo);
        if (todo.getRoutineInfo() != null) {
            routineInfoRepository.delete(todo.getRoutineInfo());
        }
        Result result = resultRepository.findByTodo(
                todo
        ).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo에 해당하는 Result가 없습니다.")
        );
        resultRepository.delete(result);
        todoRepository.delete(todo);
        return new DeleteTodoResponseDto(true);
    }

    public PokeResponseDto poke(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo가 없습니다.")
        );
        validateDate(todo);
        int pokesNum = getPokesNum(todo, user);
        validatePoke(todo, user, pokesNum);

        Poke poke = new Poke(
                user,
                todo
        );
        pokeRepository.save(poke);
        return new PokeResponseDto(LocalDateTime.now(), MAX_POKE - 1 - pokesNum);
    }

    public CheerResponseDto cheer(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo가 없습니다.")
        );
        validateDate(todo);
        validateCheer(todo, user);

        Cheer cheer = new Cheer(
                user,
                todo
        );
        cheerRepository.save(cheer);
        return new CheerResponseDto(LocalDateTime.now(), true);
    }

    public void deleteUserTownTodo(User user, Town town) {
        List<Todo> todoList = todoRepository.findTodosByTownId(town.getId(), user.getId());
        for(Todo todo : todoList){
            todoRepository.delete(todo);
        }
    }


    private void checkPermission(Todo todo, User user) {
        if (!user.getId().equals(todo.getUser().getId())) {
            throw new IllegalArgumentException("해당 Todo에 대한 권한이 없습니다.");
        }
    }

    private void validateDate(Todo todo) {
        if (!todo.getCreatedAt().toLocalDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("해당 Todo는 이미 종료됐습니다.");
        }
    }

    private void validatePoke(Todo todo, User user, int pokesNum) {
        if (todo.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 Todo에는 찌를 수 없습니다.");
        }

        if (pokesNum == MAX_POKE)
        {
            throw new IllegalArgumentException("최대 찌르기 횟수에 도달했습니다.");
        }

        if(!isSameTome(todo.getUser(), user)) {
            throw new IllegalArgumentException("해당 Todo의 주인과 같은 타운에 있지 않습니다.");
        }
    }

    private void validateCheer(Todo todo, User user) {
        if (todo.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 Todo에는 응원할 수 없습니다.");
        }

        Optional<Cheer> cheer = cheerRepository.findByUserId(todo.getUser().getId(), todo.getId());
        if (cheer.isPresent()) {
            throw new IllegalArgumentException("이미 응원했습니다.");
        }
    }

    private int getPokesNum(Todo todo, User user) {
        List<Poke> pokes = pokeRepository.findByUserId(todo.getUser().getId(), todo.getId());
        return pokes.size();
    }

    private boolean isSameTome(User userA, User userB)
    {
        return townMemberRepository.countByTwoUserId(userA.getId(), userB.getId()) > 0;
    }

}
