package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoResponseDto {
    private LocalDate date;
    private String content;
    private String position;
    private String font;
    private Integer fontSize;
}
