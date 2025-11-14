package com.example.scheduleproject.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    // User 예외 메시지
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."),

    // Schedule 예외 메시지
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."),
    SCHEDULE_NO_PERMISSION(HttpStatus.UNAUTHORIZED, "일정을 수정/삭제할 권한이 없습니다."),
    SCHEDULE_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "일정 제목은 필수입니다."),

    // Comment 예외 메시지
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    COMMENT_NO_PERMISSION(HttpStatus.UNAUTHORIZED, "댓글을 수정/삭제할 권한이 없습니다."),
    COMMENT_MAX_EXCEEDED(HttpStatus.BAD_REQUEST, "이 일정에는 더 이상 댓글을 작성할 수 없습니다. (최대 10개)");


    private final HttpStatus status;
    private final String message;
}
