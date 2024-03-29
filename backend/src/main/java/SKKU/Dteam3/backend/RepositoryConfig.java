package SKKU.Dteam3.backend;

import SKKU.Dteam3.backend.repository.UserRepositoryDB;
import SKKU.Dteam3.backend.repository.UserRepository;
import SKKU.Dteam3.backend.repository.TownRepository;
import SKKU.Dteam3.backend.repository.TownRepositoryDB;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public TownRepository townRepository(EntityManager em) {
        return new TownRepositoryDB(em);
    }

    @Bean
    public UserRepository userRepository(EntityManager em) {
        return new UserRepositoryDB(em);
    }
}
