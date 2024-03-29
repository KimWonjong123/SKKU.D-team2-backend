package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.*;
import SKKU.Dteam3.backend.dto.*;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.repository.*;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final MemoRepository memoRepository;

    private final TownMemberRepository townMemberRepository;

    private final TownRepository townRepository;

    private final TodoRepository todoRepository;

    private final ResultRepository resultRepository;

    private final GuestbookRepository guestbookRepository;

    private final KakaoApi kakaoApi;

    public KakaoLoginResponseDto saveOrUpdate(KakaoTokenResponse kakaoTokenResponse) {
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoApi.getUserInfo(kakaoTokenResponse.getAccess_token());
        KakaoAccount kakaoAccount = kakaoUserInfoResponse.getKakao_account();
        KakaoProfile kakaoProfile = kakaoAccount.getProfile();
        Optional<User> userOptional = userRepository.findById(kakaoUserInfoResponse.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getName().equals(kakaoProfile.getNickname())) {
                user.changeName(kakaoProfile.getNickname());
            }
            if  (!user.getProfileImg().equals(kakaoProfile.getThumbnail_image_url())){
                user.changeProfileImg(kakaoProfile.getThumbnail_image_url());
            }
            userRepository.update(user);
        } else {
            User user = new User(kakaoUserInfoResponse.getId(), kakaoProfile.getNickname(), kakaoProfile.getThumbnail_image_url());
            userRepository.save(user);
        }
        return new KakaoLoginResponseDto(
                kakaoTokenResponse.getAccess_token(),
                kakaoTokenResponse.getRefresh_token(),
                kakaoProfile.getThumbnail_image_url());
    }

    public MemoResponseDto getMemo(Long userId, User user, LocalDate date) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        Memo memo;
        try {
            memo = memoRepository.findByIdAndDate(userId, date);
        } catch (NoResultException e) {
            if (date.equals(LocalDate.now())) {
                memo = new Memo(userFound, "", "", "", 0);
                memoRepository.save(memo);
                return new MemoResponseDto(memo.getDate(), memo.getContent(), memo.getPosition(), memo.getFont(), memo.getFontSize());
            }
            else throw new IllegalArgumentException("해당 날짜의 메모가 존재하지 않습니다.");
        }
    	return new MemoResponseDto(memo.getDate(), memo.getContent(), memo.getPosition(), memo.getFont(), memo.getFontSize());
    }

    public MemoResponseDto getMyMemo(User user, LocalDate date) {
        Memo memo;
        try {
            memo = memoRepository.findByIdAndDate(user.getId(), date);
        } catch (NoResultException e) {
            if (date.equals(LocalDate.now())) {
                memo = new Memo(user, "", "", "", 0);
                memoRepository.save(memo);
                return new MemoResponseDto(memo.getDate(), memo.getContent(), memo.getPosition(), memo.getFont(), memo.getFontSize());
            }
            else throw new IllegalArgumentException("해당 날짜의 메모가 존재하지 않습니다.");
        }
    	return new MemoResponseDto(memo.getDate(), memo.getContent(), memo.getPosition(), memo.getFont(), memo.getFontSize());
    }

    public MemoResponseDto updateMemo(User user, UpdateMemoRequestDto requestDto) {
        Memo memo;
        try {
            memo = memoRepository.findByIdAndDate(user.getId(), LocalDate.now());
        } catch (NoResultException e) {
            memo = new Memo(user, requestDto.getContent(), requestDto.getPosition(), requestDto.getFont(), requestDto.getFontSize());
            memoRepository.save(memo);
            return new MemoResponseDto(memo.getDate(), memo.getContent(), memo.getPosition(), memo.getFont(), memo.getFontSize());
        }
        return new MemoResponseDto(memo.getDate(), memo.getContent(), memo.getPosition(), memo.getFont(), memo.getFontSize());
    }

    public ListDto<TownResponseDto> getTown(Long userId, User user) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        return ListDto.createTowns(townRepository.findByUserId(userId));
    }

    public ListDto<TodoDetail> getTodo(Long userId, User user, LocalDate date) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        return ListDto.createTodoDetails(todoRepository.findDetailByUserIdAndDate(userFound, date));
    }

    public ListDto<TodoDetail> getMyTodo(User user, LocalDate date) {
        return ListDto.createTodoDetails(todoRepository.findDetailByUserIdAndDate(user, date));
    }

    public AchieveResponseDto getAchieve(Long userId, User user, LocalDate date) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        return new AchieveResponseDto(date, convertAchieve(resultRepository.calculateAchievementRateByUser(userFound, date.atStartOfDay())));
    }

    public AchieveResponseDto getMyAchieve(User user, LocalDate date) {
        return new AchieveResponseDto(date, convertAchieve(resultRepository.calculateAchievementRateByUser(user, date.atStartOfDay())));
    }

    public ListDto<AchieveResponseDto> getCalendar(Long userId, User user, LocalDate date) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        LocalDateTime start = date.plusDays(-date.getDayOfMonth() + 1).atStartOfDay();
        LocalDateTime end = date.plusDays(-date.getDayOfMonth() + 1).plusMonths(1).atStartOfDay();
        return ListDto.createAchieves(resultRepository.calculateMonthAchievementRateByUser(user, start, end).stream().toList());
    }

    public ListDto<AchieveResponseDto> getMyCalendar(User user, LocalDate date) {
        LocalDateTime start = date.plusDays(-date.getDayOfMonth() + 1).atStartOfDay();
        LocalDateTime end = date.plusDays(-date.getDayOfMonth() + 1).plusMonths(1).atStartOfDay();
        return ListDto.createAchieves(resultRepository.calculateMonthAchievementRateByUser(user, start, end).stream().toList());
    }

    public ListDto<GuestbookResponseDto> getGuestbook(Long userId, User user, LocalDate date) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        return ListDto.createGuestbooks(guestbookRepository.findByUserAndDate(userFound, date));
    }

    public ListDto<GuestbookResponseDto> getMyGuestbook(User user, LocalDate date) {
        return ListDto.createGuestbooks(guestbookRepository.findByUserAndDate(user, date));
    }

    public ListDto<GuestbookResponseDto> saveOrUpdateGuestbook(Long userId, User user, GuestbookRequestDto requestDto) {
        User userFound = validateUserId(userId);
        if (!isSameTome(user, userFound)) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        Guestbook guestbook;
        Optional<Guestbook> guestbookOptional = guestbookRepository.findByWriter(user);
        if (guestbookOptional.isPresent()) {
            guestbook = guestbookOptional.get();
            guestbook.updateGuestbook(requestDto.getContent(), requestDto.getPosition(), requestDto.getFont(), requestDto.getFontSize());
            guestbookRepository.update(guestbook);
        } else {
            guestbook = new Guestbook(userFound, user, requestDto.getContent(), requestDto.getPosition(), requestDto.getFont(), requestDto.getFontSize());
            guestbookRepository.save(guestbook);
        }
        return ListDto.createGuestbooks(guestbookRepository.findByUserAndDate(user, LocalDate.now()));
    }

    public void leave(User user) {
        userRepository.delete(user);
    }

    private User validateUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        return userOptional.get();
    }

    private Integer convertAchieve(Float achieve){
        return (int) Math.floor(achieve * 5) * 20;
    }


    private boolean isSameTome(User userA, User userB)
    {
        return townMemberRepository.countByTwoUserId(userA.getId(), userB.getId()) > 0;
    }
}
