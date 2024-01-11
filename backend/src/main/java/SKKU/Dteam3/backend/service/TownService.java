package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Todo;
import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.repository.ResultRepository;
import SKKU.Dteam3.backend.repository.TodoRepository;
import SKKU.Dteam3.backend.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownService {

    private final TownRepository townRepository;

    private final TownThumbnailService townThumbnailService;

    private final TownMemberService townMemberService;

    private final TodoService todoService;

    private final TodoRepository todoRepository;

    private final ResultRepository resultRepository;

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
        townRepository.save(town);
        townThumbnailService.addTownThumbnail(thumbnailFile, town);
        townMemberService.saveMemberShip(user,town);
        for(AddTodoRequestDto dto : requestDto.getTownRoutine()){
            todoService.addTownTodo(new AddTodoRequestDto(
                    dto.getContent(),
                    town.getDescription(),
                    true,
                    dto.getEndDate(),
                    dto.getMon(),
                    dto.getTue(),
                    dto.getWed(),
                    dto.getThu(),
                    dto.getFri(),
                    dto.getSat(),
                    dto.getSun()
            ),user, town);
        }
        return new AddTownResponseDto(town.getInviteLinkHash(), town.getId());
    }


    public ModifyMyTownResponseDto modifyMyTown(User user, Long townId, AddTownRequestDto requestDto) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isLeaderOfTown(user,town);
        town.updateTownInfo(requestDto.getName(),requestDto.getDescription());
        townRepository.update(town);
        for(AddTodoRequestDto todos : requestDto.getTownRoutine()){
            todoService.saveOrUpdate(new AddTodoRequestDto(
                    todos.getContent(),
                    town.getDescription(),
                    true,
                    todos.getEndDate(),
                    todos.getMon(),
                    todos.getTue(),
                    todos.getWed(),
                    todos.getThu(),
                    todos.getFri(),
                    todos.getSat(),
                    todos.getSun()
            ),user, town);
        }
        List<User> Members = townMemberService.findMembersByTownId(townId, user.getId());
        updateMembersTownTodo(town, requestDto.getTownRoutine(), Members);
        return new ModifyMyTownResponseDto(townId, LocalDateTime.now());
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
                ListDto.createTownTodoInfo(todoRepository.findByTownId(town))
        );
    }


    public deleteMyTownResponseDto deleteTown(User user, Long townId) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isLeaderOfTown(user,town);
        townThumbnailService.delete(townId);
        townMemberService.removeMemberShip(townId);
        todoService.removeTownTodo(townId);
        townRepository.delete(town);
        return new deleteMyTownResponseDto(true, town.getId());
    }

    public Resource downloadTownThumbnail(Long townId, User user) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user,town);
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

    public joinTownResponseDto joinTown(String inviteLink, User user) {
        Town town = townRepository.findByInviteLink(inviteLink).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 초대링크입니다."));
        isNotMemberOfTown(user,town);
        if(!townMemberService.saveMemberShip(user,town).equals(user.getId())){
            throw new RuntimeException("타운 멤버 저장에 실패하였습니다");
        }
        town.increaseMemberNum();
        townRepository.update(town);
        List<User> users =new ArrayList<>();
        users.add(user);
        List<AddTodoRequestDto> requestDtoList = todoService.getTownTodo(town);
        for(AddTodoRequestDto dto : requestDtoList){
            todoService.addTownTodo(new AddTodoRequestDto(
                    dto.getContent(),
                    town.getDescription(),
                    true,
                    dto.getEndDate(),
                    dto.getMon(),
                    dto.getTue(),
                    dto.getWed(),
                    dto.getThu(),
                    dto.getFri(),
                    dto.getSat(),
                    dto.getSun()
            ),user, town);
        }
        return new joinTownResponseDto(town.getId(), LocalDateTime.now());
    }

    public Boolean expelMember(User user, Long townId, Long userId) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isLeaderOfTown(user,town);
        townMemberService.removeMemberShip(townId, userId);
        deleteTownTodo(user, town);
        return true;
    }
    public Boolean leaveMember(User user, Long townId) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user, town);
        townMemberService.removeMemberShip(townId,user.getId());
        deleteTownTodo(user,town);
        return true;
    }

    public memberAchieveListResponseDto getMemberAchieveList(User user, Long townId, LocalDate date) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user, town);
        List<User> userList = townMemberService.findMembersByTownId(townId, town.getLeader().getId());
        List<memberAchieveResponseDto> achieveList = new ArrayList<>();
        for(User users : userList){
            LocalDateTime start = date.plusDays(-date.getDayOfMonth() + 1).atStartOfDay();
            LocalDateTime end = date.plusDays(-date.getDayOfMonth() + 1).plusMonths(1).atStartOfDay();
            achieveList.add(new memberAchieveResponseDto(
                    user.getId(),
                    user.getName(),
                    resultRepository.calculateMonthTownAchievementRateByUser(
                            user,
                            townId,
                            start,
                            end
                        )
                    )
            );
        }
        return new memberAchieveListResponseDto(
                townId,
                achieveList
        );
    }
    public TownAchieveResponseDto getTownAchieve(User user, Long townId, LocalDate localDate) {
        memberAchieveListResponseDto memberAchieveList = getMemberAchieveList(user, townId, localDate);
        return new TownAchieveResponseDto(memberAchieveList.getTownId(),
                memberAchieveList.getMembers().stream().mapToInt(memberAchieveResponseDto::getAchieve).sum()/memberAchieveList.getMembers().size()
        );
    }

    public AddTownTodoResponseDto addTownTodo(Long townId, User user, AddTodoRequestDto requestDto) {
        Town town = townRepository.findByTownId(townId).orElseThrow(
                () -> new IllegalArgumentException("해당 Town이 없습니다.")
        );
        isMemberOfTown(user, town);
        requestDto = new AddTodoRequestDto(
                requestDto.getContent(),
                requestDto.getTodoClass(),
                false,
                requestDto.getEndDate(),
                requestDto.getMon(),
                requestDto.getTue(),
                requestDto.getWed(),
                requestDto.getThu(),
                requestDto.getFri(),
                requestDto.getSat(),
                requestDto.getSun()
        );
        AddTodoResponseDto addTodoResponseDto = todoService.addTownTodo(requestDto, user, town);
        return new AddTownTodoResponseDto(addTodoResponseDto.getCreatedAt(), addTodoResponseDto.getId());
    }

    public modifyTownTodoResponseDto modifyTownTodo(Long todoId, AddTodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 투두가 없습니다.")
        );
        isOwnedByUser(user, todo);
        todo.modifyTodo(requestDto);
        todoRepository.update(todo);
        return new modifyTownTodoResponseDto(LocalDateTime.now(), todo.getId());
    }

    public deleteTownTodoResponseDto deleteTownTodo(Long townId, Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 투두가 없습니다.")
        );
        isOwnedByUser(user, todo);
        todoService.deleteTodo(todo.getId(), user);
        return new deleteTownTodoResponseDto(todo.getId());
    }

    private Boolean isOwnedByUser(User user, Todo todo) {
        return todo.getUser().getId().equals(user.getId());
    }
    private void deleteTownTodo(User user, Town town) {
        todoService.deleteUserTownTodo(user, town);
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

    private void updateMembersTownTodo(Town town, List<AddTodoRequestDto> requestDto, List<User> members) {
        for(User user : members){
            todoService.saveOrUpdate(requestDto,user,town);
        }
    }

}
