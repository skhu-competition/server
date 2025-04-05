package server.favoritePlace.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.favoritePlace.api.response.FavoritePlaceListResDto;
import server.favoritePlace.api.response.FavoritePlaceResDto;
import server.favoritePlace.domain.FavoritePlace;
import server.favoritePlace.domain.repository.FavoritePlaceRepository;
import server.place.domain.Place;
import server.place.domain.repository.PlaceRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoritePlaceService {

    private final FavoritePlaceRepository favoritePlaceRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    // 즐겨찾기 생성 및 삭제
    @Transactional
    public FavoritePlaceResDto favoriteCreateAndDelete(Long userId, Long placeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));

        Optional<FavoritePlace> existingFavorite = favoritePlaceRepository.findByUserAndPlace(user, place);

        if (existingFavorite.isPresent()) {
            favoritePlaceRepository.delete(existingFavorite.get());
            return FavoritePlaceResDto.from(
                    existingFavorite.get(),
                    "삭제됨"
            );
        }

        FavoritePlace favorite = FavoritePlace.builder()
                .user(user)
                .place(place)
                .build();
        favoritePlaceRepository.save(favorite);

        return FavoritePlaceResDto.from(
                favorite,
                "추가됨"
        );
    }


    // 특정 유저의 즐겨찾기 목록 조회
    public FavoritePlaceListResDto findByUserId(Long userId) {
        List<FavoritePlace> favorites = favoritePlaceRepository.findAllByUser_UserId(userId);

        List<FavoritePlaceResDto> favoritePlaceResDtoList = favorites.stream()
                .map(favorite -> FavoritePlaceResDto.from(favorite, "조회"))
                .toList();

        return FavoritePlaceListResDto.from(favoritePlaceResDtoList);
    }
}
