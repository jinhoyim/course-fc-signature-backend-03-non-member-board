package course.fastcampus.signature_backend_path.simpleboard.post.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PostResponse(
        Long id,
        Long boardId,
        String username,
        String email,
        PostStatus status,
        String title,
        String content,
        LocalDateTime postedAt,
        List<ReplyEntity> replies
) {

    public PostResponse(
            Long id,
            Long boardId,
            String username,
            String email,
            PostStatus status,
            String title,
            String content,
            LocalDateTime postedAt
    ) {
        this(
                id,
                boardId,
                username,
                email,
                status,
                title,
                content,
                postedAt,
                Collections.emptyList());
    }

    public static PostResponse of(PostEntity post) {
        return new PostResponse(
                post.getId(),
                post.getBoardId(),
                post.getUsername(),
                post.getEmail(),
                post.getStatus(),
                post.getTitle(),
                post.getContent(),
                post.getPostedAt()
        );
    }

    public static PostResponse of(PostEntity post, List<ReplyEntity> replies) {
        return new PostResponse(
                post.getId(),
                post.getBoardId(),
                post.getUsername(),
                post.getEmail(),
                post.getStatus(),
                post.getTitle(),
                post.getContent(),
                post.getPostedAt(),
                replies
        );
    }
}
