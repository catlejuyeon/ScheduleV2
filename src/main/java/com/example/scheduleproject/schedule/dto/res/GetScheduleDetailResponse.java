package com.example.scheduleproject.schedule.dto.res;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleDetailResponse {
    private final Long scheduleId;
    private final String title;
    private final String content;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final List<GetCommentResponse> comments;  // 댓글 목록 추가!

    public GetScheduleDetailResponse(Long scheduleId, String title, String content,
                                     String name, LocalDateTime createdDate,
                                     LocalDateTime updatedDate, List<GetCommentResponse> comments) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.content = content;
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.comments = comments;
    }
}
