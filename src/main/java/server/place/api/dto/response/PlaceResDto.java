package server.place.api.dto.response;

import lombok.Builder;
import server.place.domain.Place;

@Builder
public record PlaceResDto(
        Long id,
        String name,
        String address,
        String description,
        double mapx,
        double mapy,
        int averageRating

) {
    public static PlaceResDto from(Place place) {
        return PlaceResDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .description(place.getDescription())
                .mapx(place.getMapx())
                .mapy(place.getMapy())
                .averageRating((int) Math.round(place.getAverageRating()))
                .build();
    }
}
