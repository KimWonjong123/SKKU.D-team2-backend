package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.Repository.TownRepository;
import SKKU.Dteam3.backend.domain.Gathering;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/town")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatherController {

    private TownRepository townRepository;

    @GetMapping("/my/{townId}")
    public String myTown(@PathVariable Long townId, Model model){
        Gathering gathering = townRepository.findBytownId(townId);
        model.addAttribute("town",gathering);
        return "town/my/{townId}";
    }

    @PostMapping("/modify/{townId}")
    public String modifyTown(@PathVariable Long townId, @ModelAttribute Gathering gathering){
        townRepository.save(gathering);
        return "town/my/{townId}";
    }

    @PostMapping("/my/{townId}/leave")
    public String leaveTown(@PathVariable Long townId, Model model){
        return "town";
    }

}
