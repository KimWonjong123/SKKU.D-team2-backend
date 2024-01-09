package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
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

        @GetMapping("/{userId}/town")
        @ResponseStatus(HttpStatus.OK)
        public ListDto<TownResponseDto> getTown(@PathVariable Long userId,
                                                Authentication authentication) {
                return userService.getTown(userId, (User) authentication.getPrincipal());
        }

        @GetMapping("/{userId}/achieve")
        @ResponseStatus(HttpStatus.OK)
        public AchieveResponseDto getAchieve(@PathVariable Long userId,
                                             Authentication authentication,
                                             @RequestParam(required = false) LocalDate date) {
                return userService.getAchieve(
                        userId,
                        (User) authentication.getPrincipal(),
                        Objects.requireNonNullElseGet(date, LocalDate::now)
                );
        }

        @GetMapping("/me/achieve")
        @ResponseStatus(HttpStatus.OK)
        public AchieveResponseDto getAchieve(Authentication authentication,
                                             @RequestParam(required = false) LocalDate date) {
                User user = (User) authentication.getPrincipal();
                return userService.getMyAchieve(
                        user,
                        Objects.requireNonNullElseGet(date, LocalDate::now)
                );
        }

        @GetMapping("/{userId}/calendar")
        @ResponseStatus(HttpStatus.OK)
        public ListDto<AchieveResponseDto> getCalendar(@PathVariable Long userId,
                                                       Authentication authentication,
                                                       @RequestParam LocalDate date) {
                return userService.getCalendar(
                        userId,
                        (User) authentication.getPrincipal(),
                        Objects.requireNonNullElseGet(date, LocalDate::now)
                );
        }

        @GetMapping("/me/calendar")
        @ResponseStatus(HttpStatus.OK)
        public ListDto<AchieveResponseDto> getCalendar(Authentication authentication,
                                                       @RequestParam LocalDate date) {
                return userService.getMyCalendar(
                        (User) authentication.getPrincipal(),
                        Objects.requireNonNullElseGet(date, LocalDate::now)
                );
        }
}
