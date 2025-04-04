package server.placeReview.api.response;

import lombok.Builder;
import server.placeReview.domain.PlaceReview;

import java.time.LocalDateTime;

@Builder
public record PlaceReviewResDto(
        Long reviewId,
        Long userId,
        String userName, // 유저 이름 or 닉네임
        String content,
        int rating,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static PlaceReviewResDto from(PlaceReview review) {
        return PlaceReviewResDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getUserId())
                .userName(review.getUser().getName())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .build();
    }
}
