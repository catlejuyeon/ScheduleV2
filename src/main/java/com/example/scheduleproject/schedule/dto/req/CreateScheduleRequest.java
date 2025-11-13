package com.example.scheduleproject.schedule.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "일정 제목은 필수입니다.")
    @Size(max = 30, message = "일정 제목은 최대 30자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "일정 내용은 필수입니다.")
    @Size(max = 200, message = "일정 내용은 최대 200자까지 입력 가능합니다.")
    private String content;

//    @NotBlank(message = "작성자명은 필수입니다.")
//    private String name;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
}
