package SKKU.Dteam3.backend.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "leader_id")
    private User leader;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @NotNull
    private int memberNum;

    @Nullable
    private String inviteLinkHash;


    public Town(User leader, String name, String description) {
        this.leader = leader;
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.memberNum = 1;
        this.inviteLinkHash = "null";//초대링크는 뒤에서 생성됨. 타운 아이디를 기반으로 만들어져야하기 떄문.
    }

    public void createInviteLink(String inviteLink) {
        this.inviteLinkHash = inviteLink;
    }
}
