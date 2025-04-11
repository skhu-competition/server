package server.auth_google.api.dto.request;
import lombok.Getter;

@Getter
public class GoogleAccesToken {
    //프론트에서 받은 엑세스 토큰
    private String accessToken;
}
