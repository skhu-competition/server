package server.favoritePlace.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record FavoritePlaceListResDto(
        List<FavoritePlaceResDto> favorites
) {
    public static FavoritePlaceListResDto from(List<FavoritePlaceResDto> favoritePlaceResDtoList) {
        return new FavoritePlaceListResDto(favoritePlaceResDtoList);
    }
}
