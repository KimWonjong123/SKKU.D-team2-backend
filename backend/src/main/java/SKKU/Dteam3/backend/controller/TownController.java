package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.Repository.TownRepository;
import SKKU.Dteam3.backend.domain.Town;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/town")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TownController {

    private TownRepository townRepository;

    @GetMapping("/town/{townId}")
    public String myTown(@PathVariable Long townId, Model model){
        Town town = townRepository.findByTownId(townId);
        model.addAttribute("town", town);
        return "town/my/{townId}";
    }

    @PostMapping("/town/{townId}")
    public String findAllMyTown(@PathVariable Long userId, Model model){
        List<Town> towns = townRepository.findByUserId(userId);
        model.addAttribute("towns", towns);
        return "town/my/{townId}";
    }

    @PostMapping("/my/{townId}/leave")
    public String leaveTown(@PathVariable Long townId, Model model){
        return "town";
    }

}
