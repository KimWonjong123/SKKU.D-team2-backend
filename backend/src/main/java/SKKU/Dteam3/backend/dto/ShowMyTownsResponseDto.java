package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowMyTownsResponseDto {
    private Long id;
    private String name;

}
