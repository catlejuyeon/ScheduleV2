package com.example.scheduleproject.schedule.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {
    private final long schedule_id;
    private final String contents;
    private final String name;
    private final String title;
    private final LocalDateTime created_date;
    private final LocalDateTime updated_date;

    public GetScheduleResponse(long scheduleId, String contents, String name, String title,
                               LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.schedule_id = scheduleId;
        this.contents = contents;
        this.name = name;
        this.title = title;
        this.created_date = createdDate;
        this.updated_date = updatedDate;
    }
}
