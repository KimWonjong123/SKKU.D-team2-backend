package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowMyTownsResponseDto {

    private Long townId;

    private String name;

    public ShowMyTownsResponseDto(Town town) {
        this.townId = town.getId();
        this.name = town.getName();
    }
}
