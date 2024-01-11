package SKKU.Dteam3.backend.domain;

import lombok.Data;

@Data
public class KakaoProfile {
    private String nickname;
    private String profile_image_url;
    private String thumbnail_image_url;
}
