package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.TownMember;
import SKKU.Dteam3.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TownRepositoryTest {
    //@Autowired
    //UserRepository;
    @Autowired
    TownRepository townRepository;

    @Test
    public void 유저의모든타운찾기() throws Exception {
        User leader1 = new User("email@email.com", "leader1");
        User member1 = new User("email@email.com","member1");
        User member2 = new User("email@email.com","member2");
        Town town1 = new Town(leader1, "town1", "descriptions about town1");
        Town town2 = new Town(leader1, "town2", "descriptions about town2");
        TownMember townMember1 = new TownMember(member1,town1);
        TownMember townMember2 = new TownMember(member2,town1);
        TownMember townMember3 = new TownMember(member1,town2);
        townRepository.saveTown(town1);
        townRepository.saveTown(town2);

    }
}