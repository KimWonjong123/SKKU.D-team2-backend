package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordOfTheDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    private LocalDateTime date;

    private String content = "";

    private String position; //아직 저장 방식 협의 안됨

    public WordOfTheDay(User user, String content, String position) {
        this.user = user;
        this.content = content;
        this.position = position;
    }

    //비지니스 로직
}
