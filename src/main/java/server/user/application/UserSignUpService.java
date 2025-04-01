package server.user.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.global.jwt.TokenProvider;
import server.user.api.dto.request.UserSignUpReqDto;
import server.user.api.dto.response.UserSignUpResDto;
import server.user.domain.User;
import server.user.domain.UserRefreshToken;
import server.user.domain.repository.UserRefreshTokenRepository;
import server.user.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final TokenProvider tokenProvider;


    @Transactional
    public UserSignUpResDto signUp(UserSignUpReqDto userSignUpReqDto) {
        if (userRepository.findByEmail(userSignUpReqDto.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = userRepository.save(User.builder()
                .email(userSignUpReqDto.email())
                .name(userSignUpReqDto.name())
                .nickname(userSignUpReqDto.nickname())
                .gender(userSignUpReqDto.gender())
                .profileImage(userSignUpReqDto.profileImage())
                .build()
        );

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setUser(user);

        userRefreshTokenRepository.deleteByUser(user);
        userRefreshTokenRepository.save(userRefreshToken);

        UserSignUpResDto userSignUpResDto = UserSignUpResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return userSignUpResDto;
    }
}