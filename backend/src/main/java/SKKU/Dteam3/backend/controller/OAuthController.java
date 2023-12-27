package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.dto.KakaoAuthRequestDto;
import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final KakaoApi kakaoApi;

    @GetMapping("/kakao/callback")
    public String kakaoCallback(String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoApi.getToken(code);
        log.info("kakaoTokenResponse : {}", kakaoTokenResponse);
        return "success";
    }
}
