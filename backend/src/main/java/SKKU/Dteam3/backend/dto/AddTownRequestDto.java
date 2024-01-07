package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddTownRequestDto {

    private String name;

    private String thumbnailName;

    private String description;

    private List<AddTodoRequestDto> townRoutine;

}
