package course.fastcampus.signature_backend_path.simpleboard.post.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PostResponse(
        Long id,
        Long boardId,
        String username,
        String email,
        PostStatus status,
        String title,
        String content,
        LocalDateTime postedAt
) {

    public static PostResponse of(PostEntity entity) {
        return new PostResponse(
                entity.getId(),
                entity.getBoardId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPostedAt()
        );
    }
}
