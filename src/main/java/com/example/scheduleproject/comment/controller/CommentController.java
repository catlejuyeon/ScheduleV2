package com.example.scheduleproject.comment.controller;

import com.example.scheduleproject.comment.dto.req.CreateCommentRequest;
import com.example.scheduleproject.comment.dto.res.CreateCommentResponse;
import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, request));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(@PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(scheduleId));
    }

    @GetMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOne(commentId));
    }
}
