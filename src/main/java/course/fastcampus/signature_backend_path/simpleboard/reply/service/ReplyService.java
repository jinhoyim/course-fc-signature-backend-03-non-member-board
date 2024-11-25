package course.fastcampus.signature_backend_path.simpleboard.reply.service;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.model.ReplyRequest;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public ReplyService(ReplyRepository replyRepository, PostRepository postRepository) {
        this.replyRepository = replyRepository;
        this.postRepository = postRepository;
    }

    public ReplyEntity create(ReplyRequest request) {
        return postRepository.findById(request.postId()).map(post -> {
            ReplyEntity entity = request.toEntity(post);
            return replyRepository.save(entity);
        }).orElseThrow();
    }
}
