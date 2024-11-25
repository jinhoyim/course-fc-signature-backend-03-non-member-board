package course.fastcampus.signature_backend_path.simpleboard.post.model;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;

import java.util.Collections;
import java.util.List;

public class PostConverter {

    private PostConverter() {
    }

    public static PostResponse fromEntity(PostEntity post) {
        return fromEntity(post, Collections.emptyList());
    }

    public static PostResponse fromEntity(PostEntity post, List<ReplyEntity> replies) {
        return new PostResponse(
                post.getId(),
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
