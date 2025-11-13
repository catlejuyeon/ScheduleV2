package com.example.scheduleproject.comment.dto.req;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommentRequest {
    private final String content;
}
