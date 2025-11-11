package com.example.scheduleproject.user.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetUserResponse {
    private final Long userId;
    private final String username;
    private final String email;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
}
