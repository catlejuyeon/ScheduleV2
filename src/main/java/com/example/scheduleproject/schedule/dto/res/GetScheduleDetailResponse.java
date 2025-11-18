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
    private final List<GetCommentResponse> comments;

    // 댓글 없는 경우를 위한 생성자 오버로딩
    public GetScheduleDetailResponse(Long scheduleId, String title, String content,
                                     String username, Long userId,
                                     LocalDateTime createdDate, LocalDateTime updatedDate) {
        this(scheduleId, title, content, username, userId, createdDate, updatedDate, null);
    }
}
