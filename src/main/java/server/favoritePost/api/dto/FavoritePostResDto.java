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
        String content,
        LocalDateTime createdAt
) {
    public static FavoritePostResDto from(FavoritePost favoritePost) {
        return FavoritePostResDto.builder()
                .favoritePostId(favoritePost.getId())
                .userId(favoritePost.getUser().getUserId())
                .postId(favoritePost.getPost().getId())
                .title(favoritePost.getPost().getTitle())
                .content(favoritePost.getPost().getContent())
                .createdAt(favoritePost.getCreatedAt())
                .build();
    }
}
