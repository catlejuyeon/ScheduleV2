package com.example.scheduleproject.user.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {

    private final Long userId;
    private final String username;
    private final String email;
}
