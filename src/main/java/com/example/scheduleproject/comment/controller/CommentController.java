package com.example.scheduleproject.comment.controller;

import com.example.scheduleproject.comment.dto.req.CreateCommentRequest;
import com.example.scheduleproject.comment.dto.res.CreateCommentResponse;
import com.example.scheduleproject.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(scheduleId, request));
    }
}
