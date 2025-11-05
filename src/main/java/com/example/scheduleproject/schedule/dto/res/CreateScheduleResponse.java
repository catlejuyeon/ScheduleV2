package com.example.scheduleproject.schedule.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {
    private final Long id;
    private final String contents;
    private final String name;
    private final String password;
    private final String title;
    private final LocalDateTime created_date;

    public CreateScheduleResponse(Long scheduleId, String title, String content, String name, String password, LocalDateTime created_date) {
        this.id = scheduleId;
        this.contents = content;
        this.name = name;
        this.password = password;
        this.title = title;
        this.created_date = created_date;
    }
}
