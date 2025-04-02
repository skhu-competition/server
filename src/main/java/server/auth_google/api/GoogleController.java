package server.auth_google.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.auth_google.api.dto.request.GoogleAccesToken;
import server.auth_google.api.dto.response.GoogleTokenDto;
import server.auth_google.application.UserGoogleLoginService;

@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/auth/google")

public class GoogleController{
    private final UserGoogleLoginService userGoogleLoginService;

    @PostMapping("/login")
    public ResponseEntity<GoogleTokenDto> googleLogin(@RequestBody GoogleAccesToken request) {
        GoogleTokenDto googleTokenDto = userGoogleLoginService.loginOrSignUp(request);
        System.out.println("구글 로그인 성공~>_<");
        return ResponseEntity.ok(googleTokenDto);
    }
}