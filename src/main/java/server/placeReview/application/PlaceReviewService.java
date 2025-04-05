package server.placeReview.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.place.domain.Place;
import server.place.domain.repository.PlaceRepository;
import server.placeReview.api.request.PlaceReviewReqDto;
import server.placeReview.api.request.PlaceReviewUpdateReqDto;
import server.placeReview.api.response.PlaceReviewListResDto;
import server.placeReview.api.response.PlaceReviewResDto;
import server.placeReview.domain.PlaceReview;
import server.placeReview.domain.repository.PlaceReviewRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceReviewService {
    private final PlaceReviewRepository placeReviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    // 후기 저장
    @Transactional
    public PlaceReviewResDto reviewSave(Long userId, Long placeId, PlaceReviewReqDto dto) {
        if (placeReviewRepository.existsByUser_UserIdAndPlace_Id(userId, placeId)) {
            throw new IllegalStateException("이미 이 장소에 후기를 작성하셨습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("장소를 찾을 수 없습니다."));

        PlaceReview review = PlaceReview.builder()
                .user(user)
                .place(place)
                .content(dto.content())
                .rating(dto.rating())
                .build();

        placeReviewRepository.save(review);

        return PlaceReviewResDto.from(review);
    }

    // 장소별 후기 조회
    @Transactional(readOnly = true)
    public PlaceReviewListResDto reviewsFindByPlaceId(Long placeId) {
        List<PlaceReview> reviews = placeReviewRepository.findAllByPlace_Id(placeId);

        List<PlaceReviewResDto> dtoList = reviews.stream()
                .map(PlaceReviewResDto::from)
                .toList();

        return PlaceReviewListResDto.from(dtoList);
    }

    // 후기 수정
    @Transactional
    public PlaceReviewResDto updateReview(Long reviewId, Long userId, PlaceReviewUpdateReqDto dto) {
        PlaceReview review = placeReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다."));

        // 🔒 작성자 본인인지 검증
        if (!review.getUser().getUserId().equals(userId)) {
            throw new SecurityException("작성자 본인만 후기를 수정할 수 있습니다.");
        }

        review.update(dto);
        return PlaceReviewResDto.from(review);
    }

    // 후기 삭제
    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        PlaceReview review = placeReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다."));

        // 🔒 작성자 본인인지 검증
        if (!review.getUser().getUserId().equals(userId)) {
            throw new SecurityException("작성자 본인만 후기를 삭제할 수 있습니다.");
        }

        placeReviewRepository.delete(review);
    }
}
