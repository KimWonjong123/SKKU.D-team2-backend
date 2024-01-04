package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryMem implements UserRepository {

    private static final Map<Long, User> store = new HashMap<>();

    private static long sequence = 0L;

    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        store.get(user.getId()).changeName(user.getName());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }
}
