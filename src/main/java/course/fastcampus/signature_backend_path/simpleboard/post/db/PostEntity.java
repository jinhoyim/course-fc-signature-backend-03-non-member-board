package course.fastcampus.signature_backend_path.simpleboard.post.db;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "post")
@Getter
@ToString
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private BoardEntity board;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime postedAt;

    protected PostEntity() {}

    private PostEntity(
            BoardEntity board,
            String username,
            String password,
            String email,
            String title,
            String content,
            LocalDateTime postedAt) {
        this.board = board;
        this.username = username;
        this.password = password;
        this.email = email;
        this.title = title;
        this.content = content;
        this.postedAt = postedAt;
        this.status = PostStatus.REGISTERED;
    }

    public static PostEntity create(
            BoardEntity board,
            String username,
            String password,
            String email,
            String title,
            String content,
            LocalDateTime postedAt) {
        return new PostEntity(
                board,
                username,
                password,
                email,
                title,
                content,
                postedAt
        );
    }

    public void remove() {
        status = PostStatus.REMOVED;
    }
}
