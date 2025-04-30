package server.auth_google.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.auth_google.api.dto.response.GoogleTokenDto;
import server.auth_google.application.UserGoogleLoginService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/auth/google")

public class GoogleController{
    private final UserGoogleLoginService userGoogleLoginService;

//    @GetMapping
//    public ResponseEntity<GoogleTokenDto> googleLogin(@RequestParam(name = "code") String code) {
//        GoogleTokenDto googleTokenDto = userGoogleLoginService.loginOrSignUp(code);
//        System.out.println("구글 로그인 성공~>_<");
//        return ResponseEntity.ok(googleTokenDto);
//    }
    @PostMapping
    public ResponseEntity<GoogleTokenDto> googleLogin(@RequestBody Map<String, String> body) {
        String accessToken = body.get("accessToken");
        GoogleTokenDto tokenDto = userGoogleLoginService.loginOrSignUp(accessToken);
        return ResponseEntity.ok(tokenDto);
    }
}
