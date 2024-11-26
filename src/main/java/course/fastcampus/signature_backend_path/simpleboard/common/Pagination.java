package course.fastcampus.signature_backend_path.simpleboard.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Pagination {

    private int page;

    private int size;

    private int current;

    private int totalPage;

    private long total;
}
