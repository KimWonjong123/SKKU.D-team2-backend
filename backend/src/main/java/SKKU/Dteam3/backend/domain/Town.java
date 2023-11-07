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
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private User leader;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @NotNull
    private Integer memberNum = 1;

    public Town(User leader, String name, String description) {
        this.leader = leader;
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    //비지니스 로직

    public boolean increaseMemberNum() {
        this.memberNum++;
        return true;
    }

    public boolean decreaseMemberNum(User user) {
        if(!existMember(user, this)) return false;
        else {
            this.memberNum--;
            return true;
        }
    }

    private boolean existMember(User user, Town town) {
        //TODO: find in TownMemberRepository
        return true;
    }

    public void modifyTown(String name, String description){
        this.name = name;
        this.description = description;
    }
}
