package server.place.api.dto.request;

import lombok.Builder;

@Builder
public record PlaceAddRequest (
        String placeName,
        String description
){
}
