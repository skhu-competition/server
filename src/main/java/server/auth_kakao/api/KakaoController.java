package server.auth_kakao.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.auth_kakao.application.KakaoService;
import server.user.api.dto.response.UserInfoRes;
import server.user.api.dto.response.UserLogInResDto;
import server.user.application.UserAuthService;
import server.user.domain.UserPrincipal;

@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    private final UserAuthService userAuthService;

    @GetMapping("/auth/kakao")
    public ResponseEntity<UserLogInResDto> kakaoLogin(@RequestParam("code") String code) {
        UserLogInResDto token = kakaoService.kakaoLogin(code);
        System.out.println("로그인 성공");
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/logOut")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = Long.parseLong(userPrincipal.getUsername());
        kakaoService.logout(userId);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/getuserinfo")
    public UserInfoRes getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userAuthService.getUserInfo(userPrincipal.getUser().getUserId());
    }
}
