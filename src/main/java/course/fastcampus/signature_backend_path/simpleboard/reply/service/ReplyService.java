package course.fastcampus.signature_backend_path.simpleboard.reply.service;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.model.ReplyRequest;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final EntityManager entityManager;

    public ReplyService(
            ReplyRepository replyRepository,
            PostRepository postRepository,
            EntityManager entityManager) {
        this.replyRepository = replyRepository;
        this.postRepository = postRepository;
        this.entityManager = entityManager;
    }

    public ReplyEntity create(ReplyRequest request) {
        if (postRepository.existsById(request.postId())) {
            PostEntity post = entityManager.getReference(PostEntity.class, request.postId());
            ReplyEntity entity = request.toEntity(post);
            return replyRepository.save(entity);
        }
        else {
            throw new NoSuchElementException("Post not found");
        }
    }
}
