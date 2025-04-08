package server.favoritePost.api.dto;

import lombok.Builder;
import server.favoritePost.domain.FavoritePost;

import java.time.LocalDateTime;

@Builder
public record FavoritePostResDto(
        Long favoritePostId,
        Long userId,
        Long postId,
        String title,
        String status,
        LocalDateTime createdAt
) {
    public static FavoritePostResDto from(FavoritePost favoritePost, String status) {
        return FavoritePostResDto.builder()
                .favoritePostId(favoritePost.getId())
                .userId(favoritePost.getUser().getUserId())
                .postId(favoritePost.getPost().getId())
                .title(favoritePost.getPost().getTitle())
                .status(status)
                .createdAt(favoritePost.getCreatedAt())
                .build();
    }
}
