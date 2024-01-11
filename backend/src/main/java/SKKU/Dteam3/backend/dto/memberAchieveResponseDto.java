package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class memberAchieveResponseDto {

    private Long userId;

    private String name;

    private Integer achieve;
}
