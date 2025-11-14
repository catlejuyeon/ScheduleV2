package com.example.scheduleproject.comment.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateCommentResponse {

    private final Long commentId;
    private final String username;
    private final long userId;
    private final LocalDateTime updatedDate;
}
