package server.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.user.domain.User;
import server.user.domain.UserRefreshToken;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    void deleteByUser(User user);
    Optional<UserRefreshToken> findByUser_UserId(Long userId);
}
