package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.service.TownService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/town")
@RequiredArgsConstructor
public class TownController {

    private final TownService townService;


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ListDto<ShowMyTownsResponseDto> showMyTowns(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ListDto.createTownList(townService.showMyTowns(user));
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AddTownResponseDto addTown (@RequestPart(value = "dto") AddTownRequestDto requestDto,
                                       @RequestPart(value = "file") MultipartFile thumbnailFile,
                                       Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.addTown(
                user,
                requestDto,
                thumbnailFile
        );
    }
    @GetMapping("/{townId}")
    @ResponseStatus(HttpStatus.OK)
    public ShowMyTownResponseDto showMyTown (@Valid @PathVariable Long townId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.showMyTown(
                user,
                townId
        );
    }

    @GetMapping("/{townId}/image")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource getTownThumbnail(@Valid @PathVariable Long townId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return townService.downloadTownThumbnail(townId, user);
    }

    @GetMapping("/{townId}/invitelink")
    @ResponseStatus(HttpStatus.OK)
    public String getInviteLink(@Valid @PathVariable Long townId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.getInviteLinkHash(townId, user);
    }

    @PostMapping("/{townId}/invitelink")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateInviteLink(@Valid @PathVariable Long townId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.updateInviteLinkHash(townId, user);
    }

    @GetMapping("/invitelink/{invitelink}")
    @ResponseStatus(HttpStatus.OK)
    public inviteTownResponseDto getInvitedTownInfo(@Valid @PathVariable(value = "invitelink")  String inviteLink, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.findTownByInviteLink(inviteLink, user);
    }

}
