package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Alarm;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.ListDto;
import SKKU.Dteam3.backend.dto.MyAlarmDto;
import SKKU.Dteam3.backend.dto.sendAlarmRequestDto;
import SKKU.Dteam3.backend.dto.sendAlarmResponseDto;
import SKKU.Dteam3.backend.repository.AlarmRepository;
import SKKU.Dteam3.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

    private final AlarmRepository alarmRepository;

    private final UserRepository userRepository;

    @Value("${alarm.max-count")
    private static int MAX_ALARM;

    public ListDto<MyAlarmDto> getMyAlarm(User user) {
        List<Alarm> alarmList = alarmRepository.findAlarmsByUserId(user.getId());
        return ListDto.createMyAlarm(alarmList);
    }


    public sendAlarmResponseDto sendAlarm(Long userId, User user, sendAlarmRequestDto requestDto) {
        User toUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("보내고자 하는 사용자가 존재하지 않습니다.")
        );
        Alarm alarm = new Alarm(
                toUser,
                user,
                requestDto.getIsTown(),
                requestDto.getTownName(),
                requestDto.getContent()
        );
        alarmRepository.save(alarm);
        return new sendAlarmResponseDto(
                alarm.getId(),
                alarm.getCreatedAt());
    }
}
