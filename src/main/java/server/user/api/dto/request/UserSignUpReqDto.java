package server.user.api.dto.request;

import server.user.domain.Gender;

public record UserSignUpReqDto(
        String email,
        String name,
        String nickname,
        Gender gender,
        String profileImage
) {
}
