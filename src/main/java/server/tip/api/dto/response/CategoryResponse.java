package server.tip.api.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long categoryId,
        String name
) {}