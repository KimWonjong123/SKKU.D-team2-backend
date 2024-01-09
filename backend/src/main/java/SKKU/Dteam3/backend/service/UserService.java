package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.*;
import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.dto.KakaoUserInfoResponse;
import SKKU.Dteam3.backend.dto.MemoResponseDto;
import SKKU.Dteam3.backend.dto.UpdateMemoRequestDto;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.repository.MemoRepository;
import SKKU.Dteam3.backend.repository.TownMemberRepository;
import SKKU.Dteam3.backend.repository.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final MemoRepository memoRepository;

    private final TownMemberRepository townMemberRepository;

    private final KakaoApi kakaoApi;

    public void saveOrUpdate(KakaoTokenResponse kakaoTokenResponse) {
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoApi.getUserInfo(kakaoTokenResponse.getAccess_token());
        KakaoAccount kakaoAccount = kakaoUserInfoResponse.getKakao_account();
        KakaoProfile kakaoProfile = kakaoAccount.getProfile();
        Optional<User> userOptional = userRepository.findById(kakaoUserInfoResponse.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getName().equals(kakaoProfile.getNickname())) {
                user.changeName(kakaoAccount.getName());
                userRepository.update(user);
            }
        } else {
            User user = new User(kakaoUserInfoResponse.getId(), kakaoProfile.getNickname());
            userRepository.save(user);
        }
    }

    public MemoResponseDto getMemo(Long userId, User user, LocalDate date) {
    	Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        if (!isSameTome(user, userOptional.get())) {
            throw new IllegalArgumentException("타운 멤버가 아닙니다.");
        }
        Memo memo;
        try {
            memo = memoRepository.findByIdAndDate(userId, date);
        } catch (NoResultException e) {
            if (date.equals(LocalDate.now())) {
                memo = new Memo(userOptional.get(), "", "", "", 0);
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

    private boolean isSameTome(User userA, User userB)
    {
        return townMemberRepository.countByTwoUserId(userA.getId(), userB.getId()) > 0;
    }
}
