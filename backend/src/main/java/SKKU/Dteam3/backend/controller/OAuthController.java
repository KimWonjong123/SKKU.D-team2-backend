package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.KakaoLeaveResponse;
import SKKU.Dteam3.backend.dto.KakaoTokenRefreshResponseDto;
import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.dto.KakaoLoginResponseDto;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final KakaoApi kakaoApi;

    private final UserService userService;

    @GetMapping("/callback/kakao")
    @ResponseStatus(HttpStatus.OK)
    public KakaoLoginResponseDto kakaoCallback(String code)  {
        KakaoTokenResponse kakaoTokenResponse = kakaoApi.getToken(code);
        log.info("kakaoTokenResponse : {}", kakaoTokenResponse);
        return userService.saveOrUpdate(kakaoTokenResponse);
    }

    @PostMapping("/kakao/leave")
    @ResponseStatus(HttpStatus.OK)
    public KakaoLeaveResponse kakaoLeave(Authentication authentication,
                                         HttpServletRequest req) {
        User user = (User) authentication.getPrincipal();
        String token = req.getHeader("authorization").split(" ")[1];
        KakaoLeaveResponse responseDto = kakaoApi.leave(user.getId(), token);
        userService.leave(user);
        return responseDto;
    }

    @PostMapping("/kakao/refresh")
    @ResponseStatus(HttpStatus.OK)
    public KakaoTokenRefreshResponseDto kakaoRefresh(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String token = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refresh"))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("refresh token이 존재하지 않습니다")).getValue();
        KakaoTokenRefreshResponseDto responseDto = kakaoApi.refresh(token);
        return responseDto;
    }
}
