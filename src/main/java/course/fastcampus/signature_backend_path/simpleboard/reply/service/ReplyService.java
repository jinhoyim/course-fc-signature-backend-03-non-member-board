package course.fastcampus.signature_backend_path.simpleboard.reply.service;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.model.ReplyRequest;
import jakarta.persistence.EntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final EntityManager entityManager;

    public ReplyService(
            ReplyRepository replyRepository,
            EntityManager entityManager) {
        this.replyRepository = replyRepository;
        this.entityManager = entityManager;
    }

    public ReplyEntity create(ReplyRequest request) {
        PostEntity post = entityManager.getReference(PostEntity.class, request.postId());
        ReplyEntity entity = request.toEntity(post);
        try {
            return replyRepository.save(entity);
        }
        catch (DataIntegrityViolationException ex) {
            String message = "답변을 저장할 수 없습니다. request: '%s'".formatted(request);
            throw new IllegalArgumentException(message, ex);
        }
    }
}
