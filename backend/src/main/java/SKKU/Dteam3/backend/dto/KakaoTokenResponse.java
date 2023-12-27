package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoTokenResponse {
    private String access_token;
    private int expires_in;
    private String token_type;
    private String id_token;
    private String refresh_token;
    private String scope;
}
