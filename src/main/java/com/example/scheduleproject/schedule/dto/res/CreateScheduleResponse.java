package com.example.scheduleproject.schedule.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateScheduleResponse {
    private final Long scheduleId;
    private final String title;
    private final String contents;
    private final String username;
    private final Long userId;
    private final LocalDateTime created_date;
}
