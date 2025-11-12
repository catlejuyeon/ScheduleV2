package com.example.scheduleproject.schedule.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateScheduleResponse {

    private final Long scheduleId;
    private final String username;
    private final String title;
    private final LocalDateTime updatedDate;
}
