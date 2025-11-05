package com.example.scheduleproject.comment.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {
    private final Long commentId;
    private final String content;
    private final String commentAuthor;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public GetCommentResponse(Long commentId, String content, String commentAuthor, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.commentId = commentId;
        this.content = content;
        this.commentAuthor = commentAuthor;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
