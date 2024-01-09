package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TownResponseDto {
    private Long id;
    private String name;

    public TownResponseDto(Town town) {
        this.id = town.getId();
        this.name = town.getName();
    }
}
