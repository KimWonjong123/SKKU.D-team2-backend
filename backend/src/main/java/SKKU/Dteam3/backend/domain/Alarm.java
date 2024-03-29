package SKKU.Dteam3.backend.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "to_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @NotNull
    private Boolean isTown;

    @Nullable
    private String townName;

    @NotNull
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    public Alarm(User user, User fromUser, Boolean isTown, @Nullable Optional<String> townName, String content) {
        this.user = user;
        this.fromUser = fromUser;
        this.isTown = isTown;
        this.townName = Objects.requireNonNull(townName).orElse("null");
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

}
