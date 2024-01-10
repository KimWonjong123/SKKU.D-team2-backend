package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.AchievementRate;
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

    public AchieveResponseDto(AchievementRate achievementRate) {
        this.date = achievementRate.getConvertedDate();
        this.achieve = achievementRate.getAchievementRate();
    }
}
