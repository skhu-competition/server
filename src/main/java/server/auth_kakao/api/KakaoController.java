package server.auth_kakao.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.auth_kakao.application.KakaoService;
import server.user.api.dto.response.UserLogInResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("kakao")
    public ResponseEntity<UserLogInResDto> kakaoLogin(@RequestParam("code") String code) {
        UserLogInResDto token = kakaoService.kakaoLogin(code);
        System.out.println("로그인 성공");
        return ResponseEntity.ok(token);
    }
}
