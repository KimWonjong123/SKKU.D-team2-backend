package SKKU.Dteam3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class sendAlarmRequestDto {

    private Boolean isTown;

    private String townName;

    private String content;

    public Optional<String> getTownName(){
        return Optional.ofNullable(this.townName);
    }

}
