package server.auth_kakao.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.auth_kakao.application.KakaoService;
import server.user.api.dto.response.UserLogInResDto;
import server.user.domain.UserPrincipal;

@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

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
}
