package server.global.jwt.dto;

import lombok.Builder;

@Builder
public record RefreshAccessTokenDto(
        String refreshAccessToken
) {
}
