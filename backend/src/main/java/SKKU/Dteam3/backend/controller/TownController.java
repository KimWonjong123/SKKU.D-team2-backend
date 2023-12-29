package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.Dto.ShowMyTownRequestDto;
import SKKU.Dteam3.backend.Dto.ShowMyTownResponseDto;
import SKKU.Dteam3.backend.Repository.TownRepository;
import SKKU.Dteam3.backend.domain.Town;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/town")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TownController {

    private TownRepository townRepository;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ShowMyTownResponseDto> showMyTown(@Valid @RequestBody ShowMyTownRequestDto requestDto){
        List<Town> town = townRepository.findByuserId(requestDto.getUserId());
        return town.stream()
                .map(ShowMyTownResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/modify/{townId}")
    public String modifyTown(@PathVariable Long townId, @ModelAttribute Town town){
        townRepository.save(town);
        return "town/my/{townId}";
    }

    @PostMapping("/my/{townId}/leave")
    public String leaveTown(@PathVariable Long townId, Model model){
        return "town";
    }

}
