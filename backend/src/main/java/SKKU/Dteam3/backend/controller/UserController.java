package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserController {
        @GetMapping("/me")
        @ResponseStatus(HttpStatus.OK)
        public User getMe(Authentication authentication) {
                return (User) authentication.getPrincipal();
        }
}
