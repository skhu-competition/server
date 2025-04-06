package server.tip.api.dto.request;

import lombok.Builder;

@Builder
public record PostRequest(
        Long categoryId,
        String title,
        String content,
        String image
) {}