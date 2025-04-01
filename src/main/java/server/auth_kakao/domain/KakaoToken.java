package server.auth_kakao.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class KakaoToken {
    @JsonProperty("access_token")
    private String accessToken;
}
