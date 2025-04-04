package server.user.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.global.jwt.TokenProvider;
import server.user.api.dto.request.UserInfo;
import server.user.api.dto.response.UserLogInResDto;
import server.user.domain.User;
import server.user.domain.UserRefreshToken;
import server.user.domain.repository.UserRefreshTokenRepository;
import server.user.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLogInService {
    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final TokenProvider tokenProvider;
    private final TokenRefreshService tokenRefreshService;

    /*@Transactional
    public UserLogInResDto logIn(UserLogInReqDto request) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // renewRefreshToken
        String createRefreshToken = tokenProvider.createRefreshToken(user);

        // refreshTokenEntity
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUser_UserId(user.getUserId())
                .orElseGet(() -> {
                    UserRefreshToken logoutUserRefreshToken = new UserRefreshToken();
                    logoutUserRefreshToken.setUser(user);
                    return userRefreshTokenRepository.save(logoutUserRefreshToken);
                });

        userRefreshToken.setRefreshToken(createRefreshToken);
        userRefreshToken.setUser(user);

        userRefreshTokenRepository.save(userRefreshToken);

        RefreshAccessTokenDto refreshAccessTokenDto = tokenRefreshService.refreshAccessToken(createRefreshToken);

        // renewAccessToken
        String refreshAccessToken = refreshAccessTokenDto.refreshAccessToken();

        UserLogInResDto userLogInResDto = UserLogInResDto.builder()
                .accessToken(refreshAccessToken)
                .refreshToken(createRefreshToken)
                .build();

        return userLogInResDto;
    }*/

    @Transactional
    public UserLogInResDto loginByKakao(UserInfo userInfo) {
        String email = userInfo.email();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(email)
                                .name(userInfo.nickname())
                                .profileImage(userInfo.profileImageUrl())
                                .build()
                ));

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        userRefreshTokenRepository.deleteByUserImmediate(user);
        userRefreshTokenRepository.save(
                new UserRefreshToken(null, refreshToken, user)
        );

        return UserLogInResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
