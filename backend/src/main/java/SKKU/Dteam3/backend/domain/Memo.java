package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private String content = "";

    @NotNull
    private String position; //아직 저장 방식 협의 안됨

    @NotNull
    private String font;

    @NotNull
    private int fontSize;

    public Memo(User user, String content, String position, String font, int fontSize) {
        this.user = user;
        this.content = content;
        this.position = position;
        this.font = font;
        this.fontSize = fontSize;
    }

    //비지니스 로직
}
