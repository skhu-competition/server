package server.auth_kakao.application;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import server.auth_kakao.api.dto.kakaoResponse.KakaoTokenResDto;
import server.auth_kakao.api.dto.kakaoResponse.KakaoUserInfo;
import server.user.api.dto.request.UserInfo;
import server.user.api.dto.response.UserInfoRes;
import server.user.api.dto.response.UserLogInResDto;
import server.user.application.UserAuthService;
import server.user.domain.UserPrincipal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoService {
    @Value("${spring.kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com"; // 액세스 토큰을 발급받기 위한 서버
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com"; // 사용자 정보를 받아오기 위한 서버

    private final UserAuthService userAuthService;

    // 인가코드를 이용해 액세스 토큰 받아오기
    public String getAccessToken(String code) {

        KakaoTokenResDto kakaoTokenResDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", KAKAO_CLIENT_ID)
                        .queryParam("redirect_uri", "http://localhost:3000/oauth/kakao") // 나중에 프론트 배포 주소로 변경해야함, 서버 배포주소 아님 ㅠㅠㅠㅠㅠ
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(KakaoTokenResDto.class)
                .block();

        return kakaoTokenResDto.getAccessToken();
    }

    // 카카오로부터 사용자 정보 가져오기
    public KakaoUserInfo getUserInfo(String accessToken) {

        KakaoUserInfo userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();

        return userInfo;
    }

    // 로그인 및 회원가입
    public UserLogInResDto kakaoLogin(String code) {
        String accessToken = getAccessToken(code);
        KakaoUserInfo userInfo = getUserInfo(accessToken);

        UserInfo user = UserInfo.builder()
                .email(userInfo.getKakaoAccount().getEmail())
                .name(userInfo.getKakaoAccount().getProfile().getNickName())
                .profileImageUrl(userInfo.getKakaoAccount().getProfile().getProfileImageUrl())
                .build();

        return userAuthService.loginByKakao(user);
    }

    public void logout(Long userId) {
        userAuthService.logout(userId);
    }

}
