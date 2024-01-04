package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PokeResponseDto {
    private LocalDateTime createdAt;
    private int remaining;
}
