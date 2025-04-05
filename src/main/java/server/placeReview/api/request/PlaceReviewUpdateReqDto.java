package server.placeReview.api.request;

import lombok.Builder;

@Builder
public record PlaceReviewUpdateReqDto(
        String content,
        int rating
) {
}
