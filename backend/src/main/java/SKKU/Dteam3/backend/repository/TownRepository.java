package SKKU.Dteam3.backend.repository;

import SKKU.Dteam3.backend.domain.Town;

import java.util.List;
import java.util.Optional;

public interface TownRepository {
    void save(Town town);

    void update(Town town);

    Optional<Town> findByTownId(Long id);

    List<Town> findByUserId(Long id);

    Optional<Town> findByTownName(String name);

    List<Town> findAll();

    Optional<Town> findByInviteLink(String inviteLink);
}
