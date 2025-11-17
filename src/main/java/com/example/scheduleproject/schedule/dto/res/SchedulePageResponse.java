package com.example.scheduleproject.schedule.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SchedulePageResponse {
    private Long scheduleId;
    private String title;
    private String content;
    private Long commentCount;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
