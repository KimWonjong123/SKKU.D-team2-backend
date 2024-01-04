package SKKU.Dteam3.backend.oauth;

import SKKU.Dteam3.backend.dto.KakaoTokenInfoResponse;
import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.dto.KakaoUserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class KakaoApi {

    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    public KakaoApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    public KakaoTokenResponse getToken(String code) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
        System.out.println(uri);

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return response.blockFirst();
    }

    public KakaoTokenInfoResponse validateToken(String accessToken) {
        Flux<KakaoTokenInfoResponse> response = webClient.get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToFlux(KakaoTokenInfoResponse.class);

        return response.blockFirst();
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }
}
