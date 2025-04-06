package server.place.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.place.domain.Place;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // 장소 이름으로 검색 (선택)
    Optional<Place> findByName(String name);

    // 주소로 중복 체크할 때 사용할 수 있음 (선택)
    Optional<Place> findByAddress(String address);

    // mapx/mapy 기준으로 정확히 찾기 (필요 시)
    Optional<Place> findByMapxAndMapy(double mapx, double mapy);

    @Query("""
    SELECT p
    FROM Place p
    LEFT JOIN p.reviews r
    GROUP BY p
    ORDER BY AVG(r.rating) DESC
    """)
    List<Place> findTop5ByAverageRating();
}
