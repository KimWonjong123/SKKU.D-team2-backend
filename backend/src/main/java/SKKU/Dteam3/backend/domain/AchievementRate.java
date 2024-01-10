package SKKU.Dteam3.backend.domain;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementRate {
    private LocalDate convertedDate;
    private Integer achievementRate;
}
