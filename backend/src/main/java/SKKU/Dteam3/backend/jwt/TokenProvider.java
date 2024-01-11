package SKKU.Dteam3.backend.jwt;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.KakaoTokenInfoResponse;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static java.lang.Long.parseLong;

@Component
@Slf4j
public class TokenProvider {

    private final long ACCESS_TOKEN_VALID_PERIOD;

    private final long REFRESH_TOKEN_VALID_PERIOD;

    private final UserRepository userRepository;

    private final Key SECRET_KEY;

    private final KakaoApi kakaoApi;

    @Autowired
    public TokenProvider(@Value("${jwt.access-token-valid-period}") long ACCESS_TOKEN_VALID_PERIOD,
                         @Value("${jwt.refresh-token-valid-period}") long REFRESH_TOKEN_VALID_PERIOD,
                         UserRepository userRepository,
                         @Value("${jwt.secret-key}") String secretKey,
                         KakaoApi kakaoApi) {
        this.ACCESS_TOKEN_VALID_PERIOD = ACCESS_TOKEN_VALID_PERIOD;
        this.REFRESH_TOKEN_VALID_PERIOD = REFRESH_TOKEN_VALID_PERIOD;
        this.userRepository = userRepository;
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.kakaoApi = kakaoApi;
    }


    public String generateToken(User userInfo, String type, long period) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + period);

        return Jwts.builder()
                .setSubject(type)
                .claim("id", userInfo.getId().toString())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(final String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            throw new MalformedJwtException("잘못된 JWT 서명입니다.");
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "만료된 JWT 토큰입니다.");
//        } catch (UnsupportedJwtException e) {
//            throw new UnsupportedJwtException("지원하지 않는 JWT 토큰입니다.");
//        } catch (IllegalArgumentException e) {
//            throw new MalformedJwtException("JWT 토큰이 잘못되었습니다.");
//        }
        try {
            kakaoApi.validateToken(token);
        } catch (Exception e) {
            throw new MalformedJwtException("JWT 토큰이 잘못되었습니다.");
        }
        return true;
    }

    public Claims parseClaims(final String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(final String token) {

        KakaoTokenInfoResponse kakaoTokenInfoResponse = kakaoApi.validateToken(token);
        Long kakaoId = kakaoTokenInfoResponse.getId();

        // 토큰 복호화
//        Claims claims = parseClaims(token);
//
//        final Long id = parseLong(claims.get("id").toString());

        //token 에 담긴 정보에 맵핑되는 User 정보 디비에서 조회
        final User user = userRepository.findById(kakaoId).get();

        //Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(user, kakaoId, null);
    }
}
