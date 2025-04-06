package server.tip.domain;

import jakarta.persistence.*;
import lombok.*;
import server.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment") //꿀팁 댓글
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String content; //댓글 내용

    @Column(name = "comment_created_at", nullable = false)
    private LocalDateTime createdAt; // 작성일

    @Column(name = "comment_modified_at", nullable = false)
    private LocalDateTime modifiedAt; // 수정일

    @PrePersist
    public void onCreate() { //entity가 처음으로 저장되거나 직전에 호출됨
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() { //entity 상태가 업데이트 되기 전에 실행되는 메소드
        this.modifiedAt = LocalDateTime.now();
    }

    @Builder
    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}