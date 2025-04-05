package server.favoritePlace.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.favoritePlace.domain.FavoritePlace;
import server.place.domain.Place;
import server.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, Long> {
    boolean existsByUserAndPlace(User user, Place place);

    Optional<FavoritePlace> findByUserAndPlace(User user, Place place);

    List<FavoritePlace> findAllByUser_UserId(Long userUserId);
}
