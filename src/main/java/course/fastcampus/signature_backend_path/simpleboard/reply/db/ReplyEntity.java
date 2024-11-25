package course.fastcampus.signature_backend_path.simpleboard.reply.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "reply")
@Getter
@Builder
@AllArgsConstructor
@ToString
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private PostEntity post;

    private String username;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ReplyStatus status = ReplyStatus.REGISTERED;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime repliedAt;

    public ReplyEntity() {

    }
}
