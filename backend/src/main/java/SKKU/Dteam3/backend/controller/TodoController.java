package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.AddTodoRequestDto;
import SKKU.Dteam3.backend.dto.AddTodoResponseDto;
import SKKU.Dteam3.backend.dto.CheckTodoResponseDto;
import SKKU.Dteam3.backend.dto.UncheckTodoResponseDto;
import SKKU.Dteam3.backend.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AddTodoResponseDto addTodo(@Valid @RequestBody AddTodoRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return todoService.addTodo(
                requestDto,
                user
        );
    }

    @PatchMapping("/{todoId}/check")
    @ResponseStatus(HttpStatus.OK)
    public CheckTodoResponseDto checkTodo(@PathVariable("todoId") Long todoId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return todoService.checkTodo(
                todoId,
                user
        );
    }

    @PatchMapping("/{todoId}/uncheck")
    @ResponseStatus(HttpStatus.OK)
    public UncheckTodoResponseDto uncheckTodo(@PathVariable("todoId") Long todoId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return todoService.uncheckTodo(
                todoId,
                user
        );
    }
}
