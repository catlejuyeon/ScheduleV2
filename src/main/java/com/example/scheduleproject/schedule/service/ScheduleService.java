package com.example.scheduleproject.schedule.service;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.schedule.dto.req.CreateScheduleRequest;
import com.example.scheduleproject.schedule.dto.req.UpdateScheduleRequest;
import com.example.scheduleproject.schedule.dto.res.CreateScheduleResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleDetailResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleResponse;
import com.example.scheduleproject.schedule.dto.res.UpdateScheduleResponse;
import com.example.scheduleproject.schedule.entity.Schedule;
import com.example.scheduleproject.schedule.repository.ScheduleRepository;
import com.example.scheduleproject.user.entity.User;
import com.example.scheduleproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
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
        Schedule schedule=scheduleRepository.findByIdWithComments(scheduleId)
                .orElseThrow(()->new IllegalStateException("없는 스케줄입니다."));

        List<GetCommentResponse> commentResponses = schedule.getComments().stream()
                .map(comment -> new GetCommentResponse(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getUser().getUserId(),
                        comment.getSchedule().getScheduleId(),
                        comment.getCreatedDate(),
                        comment.getUpdatedDate()
                ))
                .toList();
        return new GetScheduleDetailResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getUser().getUserId(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate(),
                commentResponses
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
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new IllegalStateException("없는 스케줄입니다.")
        );

        // userId 검증 (본인만 수정 가능)
        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new IllegalStateException("일정을 수정할 권한이 없습니다.");
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
                .orElseThrow(()->new IllegalStateException("없는 일정입니다."));

        // userId 검증 (본인만 삭제 가능)
        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new IllegalStateException("일정을 삭제할 권한이 없습니다.");
        }

        // 삭제
        scheduleRepository.deleteById(scheduleId);
    }
}
