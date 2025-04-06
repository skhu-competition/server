package server.tip.api.dto.request;

import lombok.Builder;

@Builder
public record CommentRequest(
        String content
) {}
