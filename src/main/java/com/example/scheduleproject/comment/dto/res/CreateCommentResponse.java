package com.example.scheduleproject.comment.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {
    private final Long commentId;
    private String content;
    private String commentAuthor;
    private final LocalDateTime created_date;

    public CreateCommentResponse(Long commentId, String content, String commentAuthor, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.content = content;
        this.commentAuthor = commentAuthor;
        this.created_date = createdDate;
    }
}
