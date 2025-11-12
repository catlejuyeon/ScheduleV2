package com.example.scheduleproject.comment.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(max = 100, message = "댓글 내용은 최대 100자입니다")
    private String content;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
}
