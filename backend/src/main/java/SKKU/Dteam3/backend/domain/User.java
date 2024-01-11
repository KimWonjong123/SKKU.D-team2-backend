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
public class User {
    @Id
    private Long id;

    @NotNull
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String profileImg;

    public User(Long id, String name, String profileImg) {
        this.id = id;
        this.name = name;
        this.profileImg = profileImg;
        this.createdAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
