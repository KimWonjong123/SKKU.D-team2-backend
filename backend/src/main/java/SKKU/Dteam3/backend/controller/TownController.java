package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.repository.TownRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/town")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TownController {

    private TownRepository townRepository;

    @GetMapping("/my/{townId}")
    public String myTown(@PathVariable Long townId, Model model){
        Town town = townRepository.findByTownId(townId).get();
        model.addAttribute("town", town);
        return "town/my/{townId}";
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
