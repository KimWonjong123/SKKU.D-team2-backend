package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookResponseDto {
    private Long userId;
    private String content;
    private String position;
    private String font;
    private Integer fontSize;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.userId = guestbook.getUser().getId();
        this.content = guestbook.getContent();
        this.position = guestbook.getPosition();
        this.font = guestbook.getFont();
        this.fontSize = guestbook.getFontSize();
    }
}
