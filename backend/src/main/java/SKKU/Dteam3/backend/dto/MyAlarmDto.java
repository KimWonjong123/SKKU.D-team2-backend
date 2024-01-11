package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyAlarmDto {

    private LocalDateTime createdAt;

    private String content;

    private String senderName;

    public MyAlarmDto(Alarm alarm) {
        this.createdAt = alarm.getCreatedAt();
        this.content = alarm.getContent();
        this.senderName =  alarm.getIsTown().equals(true)? alarm.getTownName() : alarm.getUser().getName();
    }
}
