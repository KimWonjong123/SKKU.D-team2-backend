package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.TownMember;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.repository.TownMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TownMemberService {

    private final TownMemberRepository townMemberRepository;

    public Long saveMemberShip(User user, Town town){
        TownMember townMember = new TownMember(user,town);
        townMemberRepository.save(townMember);
        return townMemberRepository.findByUserId(user.getId()).stream().filter(o -> o.getTown().equals(town))
                .findFirst().get().getUser().getId();
    }

    public List<User> findMembersByTownId(Long townId, Long leaderId) {
        return townMemberRepository.findByTownId(townId).stream().filter(o -> o.getUser().getId()!=leaderId).map(TownMember::getUser).toList();
    }
}
