package server.user.api.dto.request;

import lombok.Builder;

@Builder
public record UserInfo(
        String email,
        String nickname,
        String profileImageUrl
) {}
