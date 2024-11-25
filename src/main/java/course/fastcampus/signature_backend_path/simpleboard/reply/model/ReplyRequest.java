package course.fastcampus.signature_backend_path.simpleboard.reply.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReplyRequest(
        @NotNull Long postId,
        @NotBlank String username,
        @NotBlank @Size(min = 4, max = 20) String password,
        @NotBlank String title,
        @NotBlank String content
) {
    public ReplyEntity toEntity() {
        return ReplyEntity.builder()
                .postId(postId())
                .username(username())
                .password(password())
                .title(title())
                .content(content())
                .repliedAt(LocalDateTime.now())
                .build();
    }
}
