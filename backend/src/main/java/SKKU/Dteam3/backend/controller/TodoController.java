package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
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

    @PatchMapping("/{todoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddTodoResponseDto updateTodo(
            @PathVariable("todoId") Long todoId,
            @Valid @RequestBody AddTodoRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return todoService.updateTodo(
                todoId,
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

    @DeleteMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteTodoResponseDto deleteTodo(@PathVariable("todoId") Long todoId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return todoService.deleteTodo(
                todoId,
                user
        );
    }

    @PostMapping("/{todoId}/poke")
    @ResponseStatus(HttpStatus.CREATED)
    public PokeResponseDto poke(@PathVariable("todoId") Long todoId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return todoService.poke(
                todoId,
                user
        );
    }

    @PostMapping("/{todoId}/cheer")
    @ResponseStatus(HttpStatus.CREATED)
    public CheerResponseDto cheer(@PathVariable("todoId") Long todoId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return todoService.cheer(
                todoId,
                user
        );
    }
}
