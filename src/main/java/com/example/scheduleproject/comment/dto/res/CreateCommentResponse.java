package com.example.scheduleproject.comment.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {
    private final Long commentId;
    private final String content;
    private final String username;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDateTime created_date;
    }
