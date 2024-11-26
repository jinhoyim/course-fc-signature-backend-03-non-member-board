package course.fastcampus.signature_backend_path.simpleboard.post.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreatePostCommand(
        @NotNull
        Long boardId,

        @NotBlank
        String username,

        @NotBlank
        @Size(min = 4, max = 20)
        String password,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String title,

        @NotBlank
        String content
) {}
