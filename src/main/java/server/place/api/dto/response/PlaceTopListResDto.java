package server.place.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PlaceTopListResDto(
        List<PlaceResDtoForTop> placeTops
) {
    public static PlaceTopListResDto from(List<PlaceResDtoForTop> placeTops) {
        return PlaceTopListResDto.builder()
                .placeTops(placeTops)
                .build();
    }
}
