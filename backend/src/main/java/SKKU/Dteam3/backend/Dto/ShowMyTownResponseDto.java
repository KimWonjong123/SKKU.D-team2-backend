package SKKU.Dteam3.backend.Dto;

import SKKU.Dteam3.backend.domain.Town;

public class ShowMyTownResponseDto {

    public Long TownId;
    public String TownName;
    public ShowMyTownResponseDto(Town town) {
        TownId = town.getId();
        TownName = town.getName();
    }
}
