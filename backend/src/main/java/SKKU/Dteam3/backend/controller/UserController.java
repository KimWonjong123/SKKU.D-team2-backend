package SKKU.Dteam3.backend.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserController {
        @GetMapping("/me")
        @ResponseStatus(HttpStatus.OK)
        public String getMe() {
                return "Hello";
        }
}
