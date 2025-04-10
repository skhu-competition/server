package server.user.api.dto.response;

import lombok.Builder;

@Builder
public record UserInfoRes(
        Long userId,
        String name,
        String email
) {
}
