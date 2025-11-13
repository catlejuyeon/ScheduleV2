package com.example.scheduleproject.schedule.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteScheduleRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;
}
