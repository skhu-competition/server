package server.auth_google.application;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import server.auth_google.api.dto.GoogleMappingUserDto;
import server.auth_google.api.dto.request.GoogleAccesToken;
import server.auth_google.api.dto.response.GoogleTokenDto;
import server.global.jwt.TokenProvider;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserGoogleLoginService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    @Transactional
    public GoogleTokenDto loginOrSignUp(GoogleAccesToken googleAccesToken) {
        GoogleMappingUserDto googleUser = getGoogleUserInfo(googleAccesToken.getAccessToken());

        User user = userRepository.findByEmail(googleUser.getEmail())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(googleUser.getEmail())
                            .name(googleUser.getName())
                            .profileImage(googleUser.getProfileImage())
                            .build();
                    return userRepository.save(newUser);
                });

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        return GoogleTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private GoogleMappingUserDto getGoogleUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_USERINFO_URL,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            String json = response.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, GoogleMappingUserDto.class);
        }

        throw new RuntimeException("구글 사용자 정보를 가져오는 데 실패했습니다.");
    }
}
