package server.place.api.dto.response;

import lombok.Builder;
import server.place.domain.Place;

@Builder
public record PlaceResDtoForTop(
        Long placeId,
        String name,
        String address,
        String description,
        double mapx,
        double mapy,
        int averageRating
) {
    public static PlaceResDtoForTop from(Place place) {
        return PlaceResDtoForTop.builder()
                .placeId(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .description(place.getDescription())
                .mapx(place.getMapx())
                .mapy(place.getMapy())
                .averageRating((int) Math.round(place.getAverageRating()))
                .build();
    }
}
