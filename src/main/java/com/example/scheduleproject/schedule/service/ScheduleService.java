package com.example.scheduleproject.schedule.service;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.comment.repository.CommentRepository;
import com.example.scheduleproject.common.exception.CustomException;
import com.example.scheduleproject.common.exception.ExceptionMessage;
import com.example.scheduleproject.schedule.dto.req.CreateScheduleRequest;
import com.example.scheduleproject.schedule.dto.req.UpdateScheduleRequest;
import com.example.scheduleproject.schedule.dto.res.*;
import com.example.scheduleproject.schedule.entity.Schedule;
import com.example.scheduleproject.schedule.repository.ScheduleRepository;
import com.example.scheduleproject.user.entity.User;
import com.example.scheduleproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.USER_NOT_FOUND));
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getScheduleId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getUser().getUserId(),
                savedSchedule.getCreatedDate()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleDetailResponse findOne(Long scheduleId) {
        // 1️⃣ Schedule 조회
        GetScheduleDetailResponse schedule = scheduleRepository.findScheduleDetail(scheduleId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        // 2️⃣ 댓글은 CommentRepository에서 조회
        List<GetCommentResponse> comments = commentRepository.findCommentsByScheduleIdAsDto(scheduleId);

        // 3️⃣ 응답 생성
        return new GetScheduleDetailResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUsername(),
                schedule.getUserId(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate(),
                comments
        );
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll(Long userId) {
        List<Schedule> schedules;

        // 파라미터가 null이거나 비어있으면 전체 조회
        if (userId == null) {
            schedules = scheduleRepository.findAllByOrderByUpdatedDateDesc();
        } else {
            schedules = scheduleRepository.findAllByUser_UserIdOrderByUpdatedDateDesc(userId);
        }

        return schedules.stream()
                .map(schedule -> new GetScheduleResponse(
                        schedule.getScheduleId(),
                        schedule.getContent(),
                        schedule.getUser().getUsername(),
                        schedule.getUser().getUserId(),
                        schedule.getTitle(),
                        schedule.getCreatedDate(),
                        schedule.getUpdatedDate()
                ))
                .toList();
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request,Long userId) {
        Schedule schedule=scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new CustomException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        // userId 검증 (본인만 수정 가능)
        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new CustomException(ExceptionMessage.SCHEDULE_NO_PERMISSION);
        }

        schedule.updateSchedule(request.getTitle());

        return new UpdateScheduleResponse(
                schedule.getScheduleId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getUpdatedDate()
        );
    }

    @Transactional
    public void delete(Long scheduleId, Long userId) {
        // 일정 존재하는지 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new CustomException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        // userId 검증 (본인만 삭제 가능)
        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new CustomException(ExceptionMessage.SCHEDULE_NO_PERMISSION);
        }

        // 삭제
        scheduleRepository.deleteById(scheduleId);
    }

    // ✅ 개선된 페이징 조회
    @Transactional(readOnly = true)
    public Page<SchedulePageResponse> getScheduleWithPagination(int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        Pageable pageable = PageRequest.of(page, size);

        return scheduleRepository.findAllWithUserOrderByUpdatedDateDesc(pageable)
                .map(schedule -> {
                    // CommentRepository에서 댓글 개수 조회
                    long commentCount = commentRepository.countBySchedule_ScheduleId(schedule.getScheduleId());

                    return SchedulePageResponse.builder()
                            .scheduleId(schedule.getScheduleId())
                            .title(schedule.getTitle())
                            .content(schedule.getContent())
                            .commentCount(commentCount)
                            .username(schedule.getUser().getUsername())
                            .createdDate(schedule.getCreatedDate())
                            .updatedDate(schedule.getUpdatedDate())
                            .build();
                });
    }
}
