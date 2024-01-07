package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownService {

    private final TownRepository townRepository;

    private final TownThumbnailService townThumbnailService;

    private final TodoService todoService;

    public List<ShowMyTownsResponseDto> showMyTowns(User user) {
        List<Town> allTown = townRepository.findByUserId(user.getId());
        List<ShowMyTownsResponseDto> towns = new ArrayList<>();

        for(Town town : allTown){
            ShowMyTownsResponseDto showMyTownsResponseDto = new ShowMyTownsResponseDto(town.getId(), town.getName());
            towns.add(showMyTownsResponseDto);
        }
        return towns;
    }

    public AddTownResponseDto addTown(User user, AddTownRequestDto requestDto, MultipartFile thumbnailFile) {
        try {
            Town town = new Town(user, requestDto.getName(), requestDto.getDescription());
            town.createInviteLink(this.getURI(town.getId()));
            townThumbnailService.addTownThumbnail(thumbnailFile, town);
            townRepository.save(town);//TODO: 투두 저장하기
            return new AddTownResponseDto(town.getInviteLink(), town.getId());
        }catch(NullPointerException e){
            throw new IllegalArgumentException("Town 상세 정보가 누락되었습니다.");
        }

    }

    private String getURI(Long id) {
        return "AA"; //TODO: invite link 어떻게 설정 할 건지 결정.
    }

    public ShowMyTownResponseDto showMyTown(User user, Long townId) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user,town);
        return new ShowMyTownResponseDto(
                town.getName(),
                town.getDescription(),
                town.getMemberNum(),
                town.getLeader().getName(),
                new ArrayList<>()//TODO: 루틴투두 반환 작업하기
        );
    }

    private void isMemberOfTown(User user, Town town) {
        if(townRepository.findByUserId(user.getId()).isEmpty()){
            throw new IllegalArgumentException("해당 Town의 Member가 아닙니다.");
        }
    }
}
