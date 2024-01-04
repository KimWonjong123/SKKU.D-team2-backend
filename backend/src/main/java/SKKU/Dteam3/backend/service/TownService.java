package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.ShowMyTownsResponseDto;
import SKKU.Dteam3.backend.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownService {

    private final TownRepository townRepository;

    public List<ShowMyTownsResponseDto> showMyTowns(User user) {
        List<Town> allTown = townRepository.findByUserId(user.getId());
        List<ShowMyTownsResponseDto> showMyTownsResponseDtoList = new ArrayList<>();

        for(Town town : allTown){
            ShowMyTownsResponseDto showMyTownsResponseDto = new ShowMyTownsResponseDto(town.getId(), town.getName());
            showMyTownsResponseDtoList.add(showMyTownsResponseDto);
        }
        return showMyTownsResponseDtoList;
    }

}
