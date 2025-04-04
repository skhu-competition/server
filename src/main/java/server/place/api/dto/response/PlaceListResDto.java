package server.place.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PlaceListResDto(
        List<PlaceResDto> places
) {
    public static PlaceListResDto from(List<PlaceResDto> places) {
        return PlaceListResDto.builder()
                .places(places)
                .build();
    }
}
