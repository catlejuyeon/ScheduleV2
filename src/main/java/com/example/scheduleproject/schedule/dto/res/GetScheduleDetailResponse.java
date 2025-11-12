package com.example.scheduleproject.schedule.dto.res;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetScheduleDetailResponse {
    private final Long scheduleId;
    private final String title;
    private final String content;
    private final String username;
    private final Long userId;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final List<GetCommentResponse> comments;  // 댓글 목록 추가!
}
