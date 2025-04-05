package server.favoritePlace.api.response;

import lombok.Builder;
import server.favoritePlace.domain.FavoritePlace;

import java.time.LocalDateTime;

@Builder
public record FavoritePlaceResDto(
        Long favoriteId,
        Long userId,
        Long placeId,
        LocalDateTime createdAt,
        String status
) {
    public static FavoritePlaceResDto from(FavoritePlace favoritePlace, String status) {
        return FavoritePlaceResDto.builder()
                .favoriteId(favoritePlace.getFavoriteId())
                .userId(favoritePlace.getUser().getUserId())
                .placeId(favoritePlace.getPlace().getId())
                .createdAt(favoritePlace.getCreatedAt())
                .status(status)
                .build();
    }
}
