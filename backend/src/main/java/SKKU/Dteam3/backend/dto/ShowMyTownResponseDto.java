package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.TodoDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowMyTownResponseDto {

    private String name;

    private String description;

    private int memberNum;

    private String leader;

    private ListDto<TownTodoInfoDto> routineInfoList;
}
