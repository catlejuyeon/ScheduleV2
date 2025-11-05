package com.example.scheduleproject.comment.service;

import com.example.scheduleproject.comment.dto.req.CreateCommentRequest;
import com.example.scheduleproject.comment.dto.res.CreateCommentResponse;
import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.comment.repository.CommentRepository;
import com.example.scheduleproject.schedule.entity.Schedule;
import com.example.scheduleproject.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request){
        //1. 일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(
                        ()-> new IllegalStateException("존재하지 않는 일정입니다."));

        //2. 댓글 개수 제한
        if(schedule.getComments().size()>=10){
            throw new IllegalStateException("이 일정에는 더 이상 댓글을 작성할 수 없습니다.");
        }

        //3. 댓글 생성
        Comment comment = new Comment(
                request.getCommentAuthor(),
                request.getContent(),
                request.getPassword(),
                schedule
        );

        //4. 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getCommentId(),
                savedComment.getContent(),
                savedComment.getCommentAuthor(),
                savedComment.getCreatedDate()
        );
    }
}
