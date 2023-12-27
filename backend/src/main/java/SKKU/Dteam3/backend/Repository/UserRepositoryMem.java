package SKKU.Dteam3.backend.Repository;

import SKKU.Dteam3.backend.domain.User;
import jakarta.validation.constraints.Null;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryMem implements UserRepository{

    private static final Map<Long, User> store = new HashMap<>();

    private static long sequence = 0L;

    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }
}