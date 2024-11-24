package course.fastcampus.signature_backend_path.simpleboard.post.model;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;

import java.time.LocalDateTime;

public record PostListItem(
        Long id,
        String username,
        String title,
        LocalDateTime postedAt
) {
    public static PostListItem of(PostEntity entity) {
        return new PostListItem(
                entity.getId(),
                entity.getUsername(),
                entity.getTitle(),
                entity.getPostedAt()
        );
    }
}
