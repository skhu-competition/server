package server.placeReview.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.place.domain.Place;
import server.placeReview.api.request.PlaceReviewUpdateReqDto;
import server.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "place_review", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "place_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private String content;
    private int rating; // 1 ~ 5
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public PlaceReview(User user, Place place, String content, int rating) {
        this.user = user;
        this.place = place;
        this.content = content;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(PlaceReviewUpdateReqDto dto) {
        this.content = dto.content();
        this.rating = dto.rating();
        this.modifiedAt = LocalDateTime.now();
    }
}
