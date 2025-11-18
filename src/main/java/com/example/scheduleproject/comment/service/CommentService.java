package com.example.scheduleproject.comment.service;

import com.example.scheduleproject.comment.dto.req.CreateCommentRequest;
import com.example.scheduleproject.comment.dto.req.UpdateCommentRequest;
import com.example.scheduleproject.comment.dto.res.CreateCommentResponse;
import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.comment.dto.res.UpdateCommentResponse;
import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.comment.repository.CommentRepository;
import com.example.scheduleproject.common.exception.CustomException;
import com.example.scheduleproject.common.exception.ExceptionMessage;
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
                .orElseThrow(()-> new CustomException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        //2. 유저 조회(댓글 작성자 정보를 저장하기 위해)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.USER_NOT_FOUND));

        //3. 댓글 개수 제한
        long commentCount = commentRepository.countBySchedule_ScheduleId(scheduleId);
        if(commentCount>=10){
            throw new CustomException(ExceptionMessage.COMMENT_MAX_EXCEEDED);
        }

        //4. 댓글 생성
        Comment comment = new Comment(
                request.getContent(),
                user,
                schedule
        );

        //5. 댓글 저장
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

    // ✅ 개선: DTO 조회로 변경
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        // 일정 존재 확인
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        // CommentRepository의 DTO 조회 메서드 사용
        return commentRepository.findCommentsByScheduleIdAsDto(scheduleId);
    }

    // ✅ 개선: 단건 조회
    @Transactional(readOnly = true)
    public GetCommentResponse getOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.COMMENT_NOT_FOUND));

        return new GetCommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getUser().getUserId(),
                comment.getSchedule().getScheduleId(),
                comment.getCreatedDate(),
                comment.getUpdatedDate()
        );
    }


    @Transactional
    public UpdateCommentResponse updateOne(Long commentId, UpdateCommentRequest request,long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new CustomException(ExceptionMessage.COMMENT_NOT_FOUND));

        // 권한 검증
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new CustomException(ExceptionMessage.COMMENT_NO_PERMISSION);
        }

        comment.update(request.getContent());
        return new UpdateCommentResponse(
                comment.getCommentId(),
                comment.getUser().getUsername(),
                comment.getUser().getUserId(),
                comment.getUpdatedDate()
        );
    }

    @Transactional
    public void delete(Long commentId,long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.COMMENT_NOT_FOUND));
        // 권한 검증
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new CustomException(ExceptionMessage.COMMENT_NO_PERMISSION);
        }
        boolean existence=commentRepository.existsById(commentId);
        if(!existence){
            throw new CustomException(ExceptionMessage.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(commentId);
    }
}
