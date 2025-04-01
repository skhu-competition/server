package server.user.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.user.api.dto.request.UserLogInReqDto;
import server.user.api.dto.request.UserSignUpReqDto;
import server.user.api.dto.response.UserLogInResDto;
import server.user.api.dto.response.UserSignUpResDto;
import server.user.application.UserLogInService;
import server.user.application.UserSignUpService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)

@RequestMapping("/test")
public class UserController {

    private final UserSignUpService userSignUpService;
    private final UserLogInService userLogInService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResDto> signUp(
            @RequestBody UserSignUpReqDto userSignUpReqDto) throws IOException {
        UserSignUpResDto userSignUpResDto = userSignUpService.signUp(userSignUpReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpResDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLogInResDto> login(@RequestBody UserLogInReqDto userLogInReqDto) {
        UserLogInResDto userLogInResDto = userLogInService.logIn(userLogInReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(userLogInResDto);
    }
}
