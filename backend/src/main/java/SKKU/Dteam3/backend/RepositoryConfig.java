package SKKU.Dteam3.backend;

import SKKU.Dteam3.backend.Repository.UserRepository;
import SKKU.Dteam3.backend.Repository.UserRepositoryDB;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public UserRepository userRepository(EntityManager em) {
        return new UserRepositoryDB(em);
    }
}
