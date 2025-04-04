package server.place.application;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.place.api.dto.response.NaverSearchResponse;
import server.place.api.dto.response.PlaceListResDto;
import server.place.api.dto.response.PlaceResDto;
import server.place.domain.Place;
import server.place.domain.repository.PlaceRepository;

import java.util.List;

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
        List<String> addressList = List.of(
                // 예시입니다.
                "앤드아워",
                "스시민",
                "곱창시대 인천"
        );

        for (String address : addressList) {
            System.out.println("[debug] 검색할 주소: " + address);
            NaverSearchResponse response = naverSearchService.searchPlace(address);
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
                        .description(item.getDescription())
                        .mapx(Double.parseDouble(item.getMapx()))
                        .mapy(Double.parseDouble(item.getMapy()))
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
}
