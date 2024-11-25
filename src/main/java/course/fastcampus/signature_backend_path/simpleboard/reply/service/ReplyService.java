package course.fastcampus.signature_backend_path.simpleboard.reply.service;

import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.model.ReplyRequest;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyEntity create(ReplyRequest request) {
        ReplyEntity entity = request.toEntity();
        return replyRepository.save(entity);
    }
}
