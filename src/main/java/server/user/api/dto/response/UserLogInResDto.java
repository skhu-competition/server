package server.user.api.dto.response;

import lombok.Builder;

@Builder
public record UserLogInResDto(
        String accessToken,
        String refreshToken
) {
}
