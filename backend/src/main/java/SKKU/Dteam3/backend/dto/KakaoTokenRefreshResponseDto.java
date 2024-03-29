package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoTokenRefreshResponseDto {
    private String access_token;
    private String token_type;
    private String id_token;
    private String refresh_token;
    private Integer expires_in;
}
