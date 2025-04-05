package server.placeReview.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PlaceReviewListResDto(
        List<PlaceReviewResDto> reviews
) {
    public static PlaceReviewListResDto from(List<PlaceReviewResDto> reviews) {
        return PlaceReviewListResDto.builder()
                .reviews(reviews)
                .build();
    }
}
