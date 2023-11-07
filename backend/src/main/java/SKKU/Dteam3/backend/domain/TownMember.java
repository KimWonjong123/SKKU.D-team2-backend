package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TownMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Town town;

    //비지니스 로직
    public TownMember(User user, Town town) {
        this.user = user;
        this.town = town;
    }

    public boolean recruitNewMember(User user, Town town){

        if(!town.increaseMemberNum()){ //정원이 가득 찬 경우
            //alertErrorMessage("member quota is full");
            return false;
        }
        //check duplicate from repository
        TownMember townMember = new TownMember(user, town);
        //save to repository

        return true;
    }

    public boolean removeMemberByLeader(User user, Town town){ //리스트는 팀원만 뜨게

        //check exist from repository
        if(!town.decreaseMemberNum(user)) return false;
        else{
            //remove from repository
            return true;
        }

    }



}
