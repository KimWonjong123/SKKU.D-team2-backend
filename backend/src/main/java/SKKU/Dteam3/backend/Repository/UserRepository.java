package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);


    List<User> findAll();
}
