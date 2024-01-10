package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookRequestDto {
    private String content;
    private String position;
    private String font;
    private Integer fontSize;
}
