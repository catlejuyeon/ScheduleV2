package com.example.scheduleproject.schedule.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetScheduleResponse {
    private final long schedule_id;
    private final String contents;
    private final String username;
    private final Long userId;
    private final String title;
    private final LocalDateTime created_date;
    private final LocalDateTime updated_date;

}
