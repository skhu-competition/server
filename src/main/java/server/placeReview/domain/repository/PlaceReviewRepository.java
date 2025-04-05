package server.placeReview.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.placeReview.domain.PlaceReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long> {

    // 장소 기준 전체 후기 조회
    List<PlaceReview> findAllByPlace_Id(Long placeId);

    // 중복 작성 방지
    boolean existsByUser_UserIdAndPlace_Id(Long userId, Long placeId);
}
