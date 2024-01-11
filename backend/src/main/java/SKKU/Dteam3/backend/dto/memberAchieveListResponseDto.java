package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class memberAchieveListResponseDto {

    private Long townId;

    private List<memberAchieveResponseDto> members;
}
