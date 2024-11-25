package course.fastcampus.signature_backend_path.simpleboard.reply.controller;

import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.model.ReplyRequest;
import course.fastcampus.signature_backend_path.simpleboard.reply.service.ReplyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("")
    public ResponseEntity<ReplyEntity> create(@Valid @RequestBody ReplyRequest request) {
        try {
            return ResponseEntity.ok(replyService.create(request));
        }
        catch (IllegalArgumentException e) {
            log.error("", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
