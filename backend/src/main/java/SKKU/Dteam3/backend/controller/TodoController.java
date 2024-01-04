package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.AddTodoRequestDto;
import SKKU.Dteam3.backend.dto.AddTodoResponseDto;
import SKKU.Dteam3.backend.dto.CheckTodoResponseDto;
import SKKU.Dteam3.backend.dto.UncheckTodoResponseDto;
import SKKU.Dteam3.backend.repository.UserRepository;
import SKKU.Dteam3.backend.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private final UserRepository userRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AddTodoResponseDto addTodo(@Valid @RequestBody AddTodoRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다.")
        );
        return todoService.addTodo(
                requestDto,
                user
        );
    }

    @PatchMapping("/{todoId}/check")
    @ResponseStatus(HttpStatus.OK)
    public CheckTodoResponseDto checkTodo(@PathVariable("todoId") Long todoId) {
        return todoService.checkTodo(
                todoId
        );
    }

    @PatchMapping("/{todoId}/uncheck")
    @ResponseStatus(HttpStatus.OK)
    public UncheckTodoResponseDto uncheckTodo(@PathVariable("todoId") Long todoId) {
        return todoService.uncheckTodo(
                todoId
        );
    }
}
