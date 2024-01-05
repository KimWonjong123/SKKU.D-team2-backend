package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShowMyTownResponseDto {

    private String name;

    private String thumbnailName;

    private String description;

    private int memberNum;

    private String leader;

    private List<AddTodoResponseDto> routineInfoList;
}
