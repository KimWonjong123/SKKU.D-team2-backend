package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownService {

    private final TownRepository townRepository;

    private final TownThumbnailService townThumbnailService;

    private final TownMemberService townMemberService;

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
        Town town = new Town(user, requestDto.getName(), requestDto.getDescription());
        town.createInviteLink(this.getURI(town.getId()));
        townRepository.save(town);//TODO: 투두 저장하기
        townThumbnailService.addTownThumbnail(thumbnailFile, town);
        townMemberService.saveMemberShip(user,town);
        return new AddTownResponseDto(town.getInviteLinkHash(), town.getId());


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


    public Resource downloadTownThumbnail(Long townId, User user) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        //isMemberOfTown(user,town);
        return townThumbnailService.downloadTownThumbnail(townId);
    }

    public String getInviteLinkHash(Long townId, User user) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isLeaderOfTown(user,town);
        return town.getInviteLinkHash();
    }

    public String updateInviteLinkHash(Long townId, User user) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isLeaderOfTown(user,town);
        town.createInviteLink(this.getURI(town.getId()));
        return town.getInviteLinkHash();
    }

    public inviteTownResponseDto findTownByInviteLink(String inviteLink, User user) {
        Town town = townRepository.findByInviteLink(inviteLink).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 초대링크입니다."));
        isNotMemberOfTown(user,town);
        return new inviteTownResponseDto(
                town.getId(),
                town.getLeader().getName(),
                town.getName()
        );
    }

    private void isLeaderOfTown(User user, Town town) {
        if(!town.getLeader().getId().equals(user.getId())){
            throw new IllegalArgumentException("타운의 리더가 아닙니다.");
        }
    }

    private void isMemberOfTown(User user, Town town) {
        if(townRepository.findByUserId(user.getId()).stream().filter(o -> o.getId().equals(town.getId())).toList().isEmpty()){
            throw new IllegalArgumentException("해당 Town의 Member가 아닙니다.");
        }
    }

    private void isNotMemberOfTown(User user, Town town) {
        if(!townRepository.findByUserId(user.getId()).stream().filter(o -> o.getId().equals(town.getId())).toList().isEmpty()){
            throw new IllegalArgumentException("이미 Town의 Member입니다.");
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
