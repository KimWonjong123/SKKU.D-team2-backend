package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.KakaoAccount;
import SKKU.Dteam3.backend.domain.KakaoProfile;
import SKKU.Dteam3.backend.domain.User;
import SKKU.Dteam3.backend.dto.KakaoTokenResponse;
import SKKU.Dteam3.backend.dto.KakaoUserInfoResponse;
import SKKU.Dteam3.backend.oauth.KakaoApi;
import SKKU.Dteam3.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final KakaoApi kakaoApi;

    public void saveOrUpdate(KakaoTokenResponse kakaoTokenResponse) {
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoApi.getUserInfo(kakaoTokenResponse.getAccess_token());
        KakaoAccount kakaoAccount = kakaoUserInfoResponse.getKakao_account();
        KakaoProfile kakaoProfile = kakaoAccount.getProfile();
        Optional<User> userOptional = userRepository.findById(kakaoUserInfoResponse.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getName().equals(kakaoProfile.getNickname())) {
                user.changeName(kakaoAccount.getName());
                userRepository.update(user);
            }
        } else {
            User user = new User(kakaoUserInfoResponse.getId(), kakaoProfile.getNickname());
            userRepository.save(user);
        }
    }
}
