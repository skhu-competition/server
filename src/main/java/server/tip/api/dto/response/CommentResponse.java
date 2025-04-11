package server.tip.api.dto.response;

import lombok.Builder;

@Builder
public record CommentResponse(
        Long commentId,
        String content,
        String authorName,
        String createdAt
) {}