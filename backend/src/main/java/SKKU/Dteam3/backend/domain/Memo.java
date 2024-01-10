package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate date;

    @NotNull
    private String content = "";

    @NotNull
    private String position; //아직 저장 방식 협의 안됨

    @NotNull
    private String font;

    @NotNull
    private int fontSize;

    public Memo(User user, String content, String position, String font, Integer fontSize) {
        this.user = user;
        this.date = LocalDate.now();
        this.content = content;
        this.position = position;
        this.font = font;
        this.fontSize = fontSize;
    }

    public Memo(User user) {
        this.user = user;
        this.date = LocalDate.now();
        this.content = "";
        this.position = "";
        this.font = "";
        this.fontSize = 0;
    }

    //비지니스 로직

    public void update(String content, String position, String font, Integer fontSize) {
        this.content = content;
        this.position = position;
        this.font = font;
        this.fontSize = fontSize;
    }
}
