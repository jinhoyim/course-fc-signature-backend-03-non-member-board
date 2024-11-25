package course.fastcampus.signature_backend_path.simpleboard.post.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;

import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PostResponse(
        Long id,
        String username,
        String email,
        PostStatus status,
        String title,
        String content,
        LocalDateTime postedAt,
        List<ReplyEntity> replies
) {
}
