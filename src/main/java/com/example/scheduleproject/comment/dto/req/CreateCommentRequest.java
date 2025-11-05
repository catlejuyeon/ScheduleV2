package com.example.scheduleproject.comment.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(max = 100, message = "댓글 내용은 최대 100자입니다")
    private String content;

    @NotBlank(message = "작성자명은 필수입니다")
    @Size(max = 20)
    private String commentAuthor;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 4, max = 100)
    private String password;
}
