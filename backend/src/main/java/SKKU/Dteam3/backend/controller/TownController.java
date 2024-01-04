package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.ShowMyTownsResponseDto;
import SKKU.Dteam3.backend.repository.TownRepository;
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
    public List<ShowMyTownsResponseDto> myTowns(@Valid @RequestBody Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return townService.showMyTowns(user);
    }

    @GetMapping("/my/{townId}")
    public String myTown(@PathVariable Long townId, Model model){
        //Town town = townRepository.findByTownId(townId).get();
        //model.addAttribute("town", town);
        return "town/";
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
