package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.KakaoLeaveResponse;
import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.dto.TokenResponseDto;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.service.UserService;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final KakaoApi kakaoApi;

    private final UserService userService;

    @GetMapping("/callback/kakao")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto kakaoCallback(String code)  {
        KakaoTokenResponse kakaoTokenResponse = kakaoApi.getToken(code);
        log.info("kakaoTokenResponse : {}", kakaoTokenResponse);
        userService.saveOrUpdate(kakaoTokenResponse);
        return new TokenResponseDto(
                kakaoTokenResponse.getAccess_token(),
                kakaoTokenResponse.getRefresh_token());
    }

    @PostMapping("/kakao/leave")
    @ResponseStatus(HttpStatus.CREATED)
    public KakaoLeaveResponse kakaoLeave(Authentication authentication,
                                         HttpServletRequest req) {
        User user = (User) authentication.getPrincipal();
        String token = req.getHeader("authorization").split(" ")[1];
        KakaoLeaveResponse responseDto = kakaoApi.leave(user.getId(), token);
        userService.leave(user);
        return responseDto;
    }
}
