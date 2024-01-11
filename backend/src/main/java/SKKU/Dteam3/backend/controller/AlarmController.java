package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.ListDto;
import SKKU.Dteam3.backend.dto.MyAlarmDto;
import SKKU.Dteam3.backend.dto.sendAlarmRequestDto;
import SKKU.Dteam3.backend.dto.sendAlarmResponseDto;
import SKKU.Dteam3.backend.service.AlarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ListDto<MyAlarmDto> getMyAlarm(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return alarmService.getMyAlarm(
                user
        );
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public sendAlarmResponseDto sendAlarm(@Valid @PathVariable Long userId,
            @RequestBody sendAlarmRequestDto requestDto,
            Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return alarmService.sendAlarm(
                userId,
                user,
                requestDto
        );
    }
}
