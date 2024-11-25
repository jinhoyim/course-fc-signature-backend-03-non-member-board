package course.fastcampus.signature_backend_path.simpleboard.reply.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity(name = "reply")
@Getter
@Builder
@AllArgsConstructor
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private String username;

    @JsonIgnore
    private String password;

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
