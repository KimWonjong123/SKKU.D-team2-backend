package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AchieveResponseDto {
    private LocalDate date;
    private Integer achieve;
}
