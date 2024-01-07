package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Town;

public class inviteTownResponseDto {

    private Long townId;

    private String LeaderName;

    private String name;

    public inviteTownResponseDto(Town town) {
        this.townId = town.getId();
        this.LeaderName = town.getLeader().getName();
        this.name = town.getName();
    }
}
