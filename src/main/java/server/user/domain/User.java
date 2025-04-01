package server.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "USER_NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "NICKNAME", length = 20, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_GENDER", nullable = false)
    private Gender gender;

    @Column(name = "USER_IMAGE")
    private String profileImage;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    @Builder
    public User(Long userId, String email, String name, String nickname, Gender gender, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.profileImage = profileImage;
    }


}
