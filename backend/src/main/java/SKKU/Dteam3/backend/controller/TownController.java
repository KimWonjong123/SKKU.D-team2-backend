package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.service.TownService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/town")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TownController {

    private TownService townService;


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ShowMyTownsResponseDto> showMyTowns(@Valid @RequestBody Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.showMyTowns(user);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AddTownResponseDto addTown (@Valid @RequestBody AddTownRequestDto requestDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.addTown(
                user,
                requestDto
        );
    }
    @GetMapping("/{townId}")
    @ResponseStatus(HttpStatus.OK)
    public ShowMyTownResponseDto showMyTown (@Valid @PathVariable Long townId, @RequestBody Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.showMyTown(
                user,
                townId
        );
    }

    @GetMapping("/{townId}/invitelink")
    @ResponseStatus(HttpStatus.OK)
    public String getInviteLink(@Valid @PathVariable Long townId, @RequestBody Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.getInviteLinkHash(townId, user);
    }

    @PostMapping("/{townId}/invitelink")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateInviteLink(@Valid @PathVariable Long townId, @RequestBody Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.updateInviteLinkHash(townId, user);
    }

    @GetMapping("/{invitelink}")
    @ResponseStatus(HttpStatus.OK)
    public inviteTownResponseDto getInvitedTownInfo(@Valid @PathVariable(value = "invitelink")  String inviteLink, @RequestBody Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.findTownByInviteLink(inviteLink, user);
    }

    @PostMapping("/modify/{townId}")
    public String modifyTown(@PathVariable Long townId, @ModelAttribute Town town){
        //townRepository.save(town);
        return "town/my/{townId}";
    }

    @PostMapping("/my/{townId}/leave")
    public String leaveTown(@PathVariable Long townId, Model model){
        return "town";
    }

}
