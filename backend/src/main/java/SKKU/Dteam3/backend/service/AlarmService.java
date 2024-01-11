package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Alarm;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.ListDto;
import SKKU.Dteam3.backend.dto.MyAlarmDto;
import SKKU.Dteam3.backend.repository.AlarmRepository;
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

    @Value("${alarm.max-count")
    private static int MAX_ALARM;

    public ListDto<MyAlarmDto> getMyAlarm(User user) {
        List<Alarm> alarmList = alarmRepository.findAlarmsByUserId(user.getId());
        return ListDto.createMyAlarm(alarmList);
    }


}
