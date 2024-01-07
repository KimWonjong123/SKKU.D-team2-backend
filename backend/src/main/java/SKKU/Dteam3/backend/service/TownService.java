package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    public AddTownResponseDto addTown(User user, AddTownRequestDto requestDto) {
        try {
            Town town = new Town(user, requestDto.getName(), requestDto.getDescription());
            town.createInviteLink(this.getURI(town.getId()));
            townRepository.save(town);
            return new AddTownResponseDto(town.getInviteLink(), town.getId());
        }catch(NullPointerException e){
            throw new IllegalArgumentException("타운 상세 정보가 누락되었습니다");
        }

    }

    public ShowMyTownResponseDto showMyTown(User user, Long townId) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user,town);
        return new ShowMyTownResponseDto(
                town.getName(),
                "thumbnailName",//TODO: 썸내일 작업하기
                town.getDescription(),
                town.getMemberNum(),
                town.getLeader().getName(),
                new ArrayList<>()//TODO: 루틴투두 반환 작업하기
        );
    }


    public String getInviteLink(Long townId, User user) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user,town);
        return town.getInviteLink();
    }

    public String updateInviteLink(Long townId, User user) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user,town);
        town.createInviteLink(this.getURI(town.getId()));
        return town.getInviteLink();
    }

    private void isMemberOfTown(User user, Town town) {
        if(townRepository.findByUserId(user.getId()).isEmpty()){
            throw new IllegalArgumentException("해당 Town의 Member가 아닙니다.");
        }
    }
    private String getURI(Long id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((id+ LocalDateTime.now().toString()).getBytes());
            return byteToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("초대 링크 생성에 실패하였습니다.");
        }
    }

    private String byteToHex(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


}
