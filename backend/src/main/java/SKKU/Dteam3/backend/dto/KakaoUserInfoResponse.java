package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.KakaoAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoResponse {

    private Long id;

    private KakaoAccount kakao_account;
}
