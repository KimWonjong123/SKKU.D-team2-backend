package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class removeMemberResponseDto {

    private Boolean success;

    private Long userId;
}
