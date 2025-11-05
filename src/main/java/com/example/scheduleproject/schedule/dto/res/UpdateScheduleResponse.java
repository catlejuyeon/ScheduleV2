package com.example.scheduleproject.schedule.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {

    private final Long scheduleId;
    private final String name;
    private final String title;
    private final LocalDateTime updatedDate;

    public UpdateScheduleResponse(Long scheduleId, String name, String title, LocalDateTime updatedDate) {
        this.scheduleId = scheduleId;
        this.name = name;
        this.title = title;
        this.updatedDate = updatedDate;
    }
}
