package server.auth_google.api.dto.response;

import lombok.Getter;

//어노테이션 확인하기.
@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
}
