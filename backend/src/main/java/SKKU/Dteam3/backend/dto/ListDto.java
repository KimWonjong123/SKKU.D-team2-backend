package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Town;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListDto <T> {
    private Integer count;
    private List<T> data;

    public static ListDto<TownResponseDto> createTowns(List<Town> towns) {
        return new ListDto<>(towns.size(), towns.stream()
                .map(TownResponseDto::new)
                .collect(Collectors.toList()));
    }
}