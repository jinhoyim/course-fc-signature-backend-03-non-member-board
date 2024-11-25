package course.fastcampus.signature_backend_path.simpleboard.reply.controller;

import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.model.ReplyRequest;
import course.fastcampus.signature_backend_path.simpleboard.reply.service.ReplyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("")
    public ReplyEntity create(@Valid @RequestBody ReplyRequest request) {
        return replyService.create(request);
    }
}
