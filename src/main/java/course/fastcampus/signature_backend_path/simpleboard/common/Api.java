package course.fastcampus.signature_backend_path.simpleboard.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Api<T> {

    private T body;

    private Pagination pagination;
}
