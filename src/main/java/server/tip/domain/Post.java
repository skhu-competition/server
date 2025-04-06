package server.tip.domain;

import jakarta.persistence.*;
import lombok.*;
import server.user.domain.User;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post") //꿀팁 게시글
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "post_id" )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name="post_title", nullable = false, length = 50)
    private String title; //제목

    @Lob //CLOB
    @Column(name = "post_content")
    private String content; //내용

    @Column(name = "post_image")
    private String image; //사진

    @Column(name="post_create_at", nullable = false)
    private LocalDateTime createdAt; //작성일

    @Column(name="post_update_at", nullable = false)
    private LocalDateTime updateAt; //수정일

    @PrePersist //entity가 처음으로 저장되거나 직전에 호출됨. 엔티티가 저장되기 전에 필요한 초기화 작업 수행하거나 생성일 설정하는 작업
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updateAt = this.createdAt;
    }

    @PreUpdate //entity 상태가 업데이트 되기 전에 실행되는 메소드
    public void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    @Builder
    public Post(User user, Category category, String title, String content, String image, boolean favorite) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public void update(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}