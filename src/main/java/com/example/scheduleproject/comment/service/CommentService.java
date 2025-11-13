package com.example.scheduleproject.comment.service;

import com.example.scheduleproject.comment.dto.req.CreateCommentRequest;
import com.example.scheduleproject.comment.dto.req.UpdateCommentRequest;
import com.example.scheduleproject.comment.dto.res.CreateCommentResponse;
import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.comment.dto.res.UpdateCommentResponse;
import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.comment.repository.CommentRepository;
import com.example.scheduleproject.schedule.entity.Schedule;
import com.example.scheduleproject.schedule.repository.ScheduleRepository;
import com.example.scheduleproject.user.entity.User;
import com.example.scheduleproject.user.repository.UserRepository;
import com.example.scheduleproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request){
        //1. 일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 일정입니다."));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        //2. 댓글 개수 제한
        if(schedule.getComments().size()>=10){
            throw new IllegalStateException("이 일정에는 더 이상 댓글을 작성할 수 없습니다.");
        }

        //3. 댓글 생성
        Comment comment = new Comment(
                request.getContent(),
                user,
                schedule
        );

        //4. 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getCommentId(),
                savedComment.getContent(),
                savedComment.getUser().getUsername(),
                savedComment.getUser().getUserId(),
                savedComment.getSchedule().getScheduleId(),
                savedComment.getCreatedDate()
        );
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 일정입니다."));

        List<Comment> comments=commentRepository.findBySchedule(schedule);
        return comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getUser().getUserId(),
                        comment.getSchedule().getScheduleId(),
                        comment.getCreatedDate(),
                        comment.getUpdatedDate()
                        )).toList();
    }

    @Transactional(readOnly = true)
    public GetCommentResponse getOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalStateException("없는 댓글입니다.")
        );
        return new GetCommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getUser().getUserId(),
                comment.getSchedule().getScheduleId(),
                comment.getCreatedDate(),
                comment.getUpdatedDate());
    }

    @Transactional
    public UpdateCommentResponse updateOne(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalStateException("없는 댓글입니다.")
        );
        comment.update(request.getContent());
        return new UpdateCommentResponse(
                comment.getCommentId(),
                comment.getUser().getUsername(),
                comment.getUpdatedDate()
        );
    }
}
