package course.fastcampus.signature_backend_path.simpleboard.post.db;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity(name = "post")
@Getter
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

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
            Long boardId,
            String username,
            String password,
            String email,
            String title,
            String content,
            LocalDateTime postedAt) {
        this.boardId = boardId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.title = title;
        this.content = content;
        this.postedAt = postedAt;
        this.status = PostStatus.REGISTERED;
    }

    public static PostEntity create(
            Long boardId,
            String username,
            String password,
            String email,
            String title,
            String content,
            LocalDateTime postedAt) {
        return new PostEntity(
                boardId,
                username,
                password,
                email,
                title,
                content,
                postedAt
        );
    }
}
