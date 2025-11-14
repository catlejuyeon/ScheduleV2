package com.example.scheduleproject.schedule.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    @NotBlank(message = "일정 제목은 필수입니다.")
    @Size(max = 30, message = "일정 제목은 최대 30자까지 입력 가능합니다.")
    private String title;

    @NotNull(message = "유저 아이디는 필수입니다.")
    private Long userId;
}
