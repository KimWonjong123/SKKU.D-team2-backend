package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> getById(Long id);

    Optional<User> getByEmail(String email);


    List<User> findAll();
}
