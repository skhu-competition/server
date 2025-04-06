package server.postLike.api.response;

import lombok.Builder;

@Builder
public record PostLikeStatusDto(
        boolean liked,
        Long likeCount
) {}