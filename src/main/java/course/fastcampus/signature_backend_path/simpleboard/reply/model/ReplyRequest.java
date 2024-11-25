package course.fastcampus.signature_backend_path.simpleboard.reply.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReplyRequest(
        @NotNull Long postId,
        @NotBlank String username,
        @NotBlank String title,
        @NotBlank String content
) {
    public ReplyEntity toEntity(PostEntity post) {
        return ReplyEntity.builder()
                .post(post)
                .username(username())
                .title(title())
                .content(content())
                .repliedAt(LocalDateTime.now())
                .build();
    }
}
