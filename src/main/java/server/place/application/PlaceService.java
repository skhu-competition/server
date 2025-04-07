package server.place.application;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.place.api.dto.response.*;
import server.place.domain.Place;
import server.place.domain.repository.PlaceRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceService {
    private final PlaceRepository placeRepository;


    private final NaverSearchService naverSearchService;

    // 장소 저장
    @PostConstruct
    public void initPlaces() {
        saveInitialPlaces();
        System.out.println("[init] 호출됨");
    }


    @Transactional
    public void saveInitialPlaces() {
        Map<String, String> placesToAdd = Map.of(
                "앤드아워", "감성적인 분위기의 디저트 카페",
                "스시민", "신선한 재료로 만드는 정통 스시 맛집",
                "곱창시대 인천", "매콤한 곱창과 볶음밥이 인기인 맛집"
        );

        for (Map.Entry<String, String> entry : placesToAdd.entrySet()) {
            String query = entry.getKey();
            String manualDescription = entry.getValue();

            System.out.println("[debug] 검색할 주소: " + query);
            NaverSearchResponse response = naverSearchService.searchPlace(query);
            System.out.println("[debug] 검색 결과 개수: " + response.getItems().size());
            if (response.getItems().isEmpty()) continue;

            NaverSearchResponse.Item item = response.getItems().get(0);

            // 중복 저장 방지
            if (placeRepository.findByAddress(item.getRoadAddress()).isPresent()) {
                System.out.println("[debug] 이미 저장된 주소: " + item.getRoadAddress());
                continue;
            }

            try {
                Place place = Place.builder()
                        .name(stripHtml(item.getTitle()))
                        .address(item.getRoadAddress())
                        .description(manualDescription)
                        .mapx(Double.parseDouble(item.getMapx()) / 10000000)
                        .mapy(Double.parseDouble(item.getMapy()) / 10000000)
                        .build();

                placeRepository.save(place);
                System.out.println("[debug] 저장 완료: " + place.getName());
            } catch (Exception e) {
                System.out.println("[error] 저장 실패: " + item.getTitle());
                e.printStackTrace();
            }
        }
    }


    private String stripHtml(String html) {
        return html.replaceAll("<[^>]*>", ""); // HTML 태그 제거
    }

    // 장소 전체 조회
    @Transactional(readOnly = true)
    public PlaceListResDto placeFindAll() {
        List<Place> places = placeRepository.findAll();

        List<PlaceResDto> placeResDtoList = places.stream()
                .map(PlaceResDto::from)
                .toList();

        return PlaceListResDto.from(placeResDtoList);
    }

    // placeId로 장소 한 개 조회
    @Transactional
    public PlaceResDto placeFindOne(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소를 찾을 수 없습니다. id=" + placeId));

        return PlaceResDto.from(place);
    }

    // top 5 장소 조회
    @Transactional(readOnly = true)
    public List<PlaceResDtoForTop> getTop5ByAverageRating() {
        return placeRepository.findTop5ByAverageRating().stream()
                .limit(5)
                .map(PlaceResDtoForTop::from)
                .toList();
    }
}
