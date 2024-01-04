package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.dto.TokenResponseDto;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final KakaoApi kakaoApi;

    private final UserService userService;

    @GetMapping("/callback/kakao")
    public TokenResponseDto kakaoCallback(String code)  {
        KakaoTokenResponse kakaoTokenResponse = kakaoApi.getToken(code);
        log.info("kakaoTokenResponse : {}", kakaoTokenResponse);
        userService.saveOrUpdate(kakaoTokenResponse);
        return new TokenResponseDto(
                kakaoTokenResponse.getAccess_token(),
                kakaoTokenResponse.getRefresh_token());
    }
}
