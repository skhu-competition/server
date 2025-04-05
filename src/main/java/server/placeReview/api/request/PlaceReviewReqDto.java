package server.placeReview.api.request;

import lombok.Builder;
import server.place.domain.Place;

@Builder
public record PlaceReviewReqDto(
        String content,
        int rating
) {
}
