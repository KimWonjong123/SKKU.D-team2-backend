package SKKU.Dteam3.backend.dto;

import SKKU.Dteam3.backend.domain.*;
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


    public static ListDto<ShowMyTownsResponseDto> createTownList(List<ShowMyTownsResponseDto> list) {
        return new ListDto<>(list.size(), list);
    }

    public static ListDto<AchieveResponseDto> createAchieves(List<AchievementRate> achieves) {
        return new ListDto<>(achieves.size(), achieves.stream()
                .map(AchieveResponseDto::new)
                .collect(Collectors.toList()));
    }

    public static ListDto<TodoDetail> createTodoDetails(List<TodoDetail> todoDetails) {
        return new ListDto<>(todoDetails.size(), todoDetails);
    }


    public static ListDto<AddTodoRequestDto> createTownTodoInfo(List<AddTodoRequestDto> townTodoInfo) {
        return new ListDto<>(townTodoInfo.size(), townTodoInfo);
    }

    public static ListDto<GuestbookResponseDto> createGuestbooks(List<Guestbook> guestbooks) {
        return new ListDto<>(guestbooks.size(), guestbooks.stream()
                .map(GuestbookResponseDto::new)
                .collect(Collectors.toList()));
    }

    public static ListDto<MyAlarmDto> createMyAlarm(List<Alarm> alarms){
        return new ListDto<>(alarms.size(), alarms.stream()
                .map(MyAlarmDto::new)
                .collect(Collectors.toList()));
    }
}
