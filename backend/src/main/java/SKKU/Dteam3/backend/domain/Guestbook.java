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
public class Guestbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @NotNull
    private String content;

    @NotNull
    private String position;

    @NotNull
    private String font;

    @NotNull
    private int fontSize;

    public Guestbook(User user, String content, String position, String font, int fontSize) {
        this.user = user;
        this.date = LocalDate.now();
        this.content = content;
        this.position = position;
        this.font = font;
        this.fontSize = fontSize;
    }

    public Guestbook(User user) {
        this.user = user;
        this.date = LocalDate.now();
        this.content = "";
        this.position = "";
        this.font = "";
        this.fontSize = 0;
    }
}
