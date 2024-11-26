package course.fastcampus.signature_backend_path.simpleboard.post.service;

import course.fastcampus.signature_backend_path.simpleboard.common.Api;
import course.fastcampus.signature_backend_path.simpleboard.common.Pagination;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(
            PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Api<List<PostListItem>> all(Pageable pageable) {
        Page<PostEntity> list = postRepository.findAllByStatus(
                PostStatus.REGISTERED,
                pageable);

        Pagination pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .current(list.getNumberOfElements())
                .totalPage(list.getTotalPages())
                .total(list.getTotalElements())
                .build();

        var items = list.stream().map(PostListItem::of).toList();

        return Api.<List<PostListItem>>builder()
                .body(items)
                .pagination(pagination)
                .build();
    }
}
