package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private User leader;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private RoutineInfo gatherRoutine;

    private String name;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;


    private Integer memberNum = 1;

    private Integer memberMax = 14;

    public void setId(Long id) {  /// temporary. Must delete when connect Mysql
        this.id = id;
    }

    public Gathering(User leader, String name, String description) {
        this.leader = leader;
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    //비지니스 로직

    public boolean increaseMemberNum() {
        if(this.memberNum.equals(this.memberMax)) return false;
        else {
            this.memberNum++;
            return true;
        }
    }

    public boolean decreaseMemberNum(User user) {
        if(!existMember(user, this)) return false;
        else {
            this.memberNum--;
            return true;
        }
    }

    private boolean existMember(User user, Gathering gathering) {
        //find in GatheringMemberRepository
        return true;
    }

    public void modifyGathering(String name, String description){
        this.name = name;
        this.description = description;
    }
}
