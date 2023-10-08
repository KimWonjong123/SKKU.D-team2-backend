package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatheringMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    //비지니스 로직
    public GatheringMember(User user, Gathering gathering) {
        this.user = user;
        this.gathering = gathering;
    }

    public boolean recruitNewMember(User user, Gathering gathering){

        if(!gathering.increaseMemberNum()){ //정원이 가득 찬 경우
            //alertErrorMessage("member quota is full");
            return false;
        }
        //check duplicate from repository
        GatheringMember gatheringMember = new GatheringMember(user, gathering);
        //save to repository

        return true;
    }

    public boolean removeMemberByLeader(User user, Gathering gathering){ //리스트는 팀원만 뜨게

        //check exist from repository
        if(!gathering.decreaseMemberNum(user)) return false;
        else{
            //remove from repository
            return true;
        }

    }



}
