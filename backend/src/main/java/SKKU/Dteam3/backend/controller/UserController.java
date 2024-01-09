package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.MemoResponseDto;
import SKKU.Dteam3.backend.dto.UpdateMemoRequestDto;
import SKKU.Dteam3.backend.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;
        @GetMapping("/me")
        @ResponseStatus(HttpStatus.OK)
        public User getMe(Authentication authentication) {
                return (User) authentication.getPrincipal();
        }

        @GetMapping("/{userId}/memo")
        @ResponseStatus(HttpStatus.OK)
        public MemoResponseDto getMemo(@PathVariable Long userId,
                                       Authentication authentication,
                                       @RequestParam(required = false) LocalDate date) {
                return userService.getMemo(
                        userId,
                        (User) authentication.getPrincipal(),
                        Objects.requireNonNullElseGet(date, LocalDate::now)
                );
        }

        @GetMapping("/me/memo")
        @ResponseStatus(HttpStatus.OK)
        public MemoResponseDto getMemo(Authentication authentication,
                                       @RequestParam(required = false) LocalDate date) {
                User user = (User) authentication.getPrincipal();
                return userService.getMyMemo(
                        user,
                        Objects.requireNonNullElseGet(date, LocalDate::now)
                );
        }

        @PostMapping("/me/memo")
        @ResponseStatus(HttpStatus.CREATED)
        public MemoResponseDto updateMemo(Authentication authentication,
                                          @RequestBody UpdateMemoRequestDto requestDto) {
                User user = (User) authentication.getPrincipal();
                return userService.updateMemo(user, requestDto);
        }
}
