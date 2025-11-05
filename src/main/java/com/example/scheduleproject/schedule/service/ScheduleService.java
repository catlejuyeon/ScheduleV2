package com.example.scheduleproject.schedule.service;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.schedule.dto.*;
import com.example.scheduleproject.schedule.dto.res.CreateScheduleResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleDetailResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleResponse;
import com.example.scheduleproject.schedule.dto.res.UpdateScheduleResponse;
import com.example.scheduleproject.schedule.entity.Schedule;
import com.example.scheduleproject.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getName(),
                request.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getScheduleId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getName(),
                savedSchedule.getPassword(),
                savedSchedule.getCreatedDate()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleDetailResponse findOne(Long scheduleId) {
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new IllegalStateException("없는 스케줄입니다.")
        );
        List<GetCommentResponse> commentResponses = schedule.getComments().stream()
                .map(comment -> new GetCommentResponse(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getCommentAuthor(),
                        comment.getCreatedDate(),
                        comment.getUpdatedDate()
                ))
                .toList();
        return new GetScheduleDetailResponse(
                schedule.getScheduleId(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate(),
                commentResponses
        );
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll(String name) {
        List<Schedule> schedules;

        // 파라미터가 null이거나 비어있으면 전체 조회
        if (name == null || name.isEmpty()) {
            schedules = scheduleRepository.findAllByOrderByUpdatedDateDesc();
        } else {
            schedules = scheduleRepository.findAllByNameOrderByUpdatedDateDesc(name.trim());
        }

        return schedules.stream()
                .map(schedule -> new GetScheduleResponse(
                        schedule.getScheduleId(),
                        schedule.getContent(),
                        schedule.getName(),
                        schedule.getTitle(),
                        schedule.getCreatedDate(),
                        schedule.getUpdatedDate()
                ))
                .toList();
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new IllegalStateException("없는 스케줄입니다.")
        );

        // 비밀번호 검증
        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        schedule.updateSchedule(
                request.getTitle(),
                request.getName());

        return new UpdateScheduleResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getName().trim(),
                schedule.getUpdatedDate()
        );
    }

    @Transactional
    public void delete(Long scheduleId,String password) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        //1. 일정 존재하는지 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                        ()->new IllegalStateException("없는 일정입니다."));

        //2. 비밀번호 검증
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //3. 삭제
        scheduleRepository.deleteById(scheduleId);
    }
}
