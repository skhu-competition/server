package server.tip.api.dto.response;
import lombok.Builder;

@Builder
public record PostResponse(
        Long postId,
        Long userId,
        String userName,
        String title,
        String content,
        String category,
        String image,
        String createdAt,
        String updateAt,
        boolean favorite

) {}
