package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.AddTownRequestDto;
import SKKU.Dteam3.backend.dto.AddTownResponseDto;
import SKKU.Dteam3.backend.dto.ShowMyTownResponseDto;
import SKKU.Dteam3.backend.dto.ShowMyTownsResponseDto;
import SKKU.Dteam3.backend.service.TownService;
import SKKU.Dteam3.backend.service.TownThumbnailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/town")
@RequiredArgsConstructor
public class TownController {

    private final TownService townService;

    private final TownThumbnailService townThumbnailService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ShowMyTownsResponseDto> showMyTowns(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.showMyTowns(user);
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
    public ShowMyTownResponseDto showMyTown (@Valid @PathVariable Long townId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.showMyTown(
                user,
                townId
        );
    }

    @GetMapping("/{townId}/image")
    public Resource getTownThumbnail(@Valid @PathVariable Long townId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.downloadTownThumbnail(townId, user);
    }

}
