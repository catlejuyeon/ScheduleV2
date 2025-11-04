package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.*;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new IllegalStateException("없는 스케줄입니다.")
        );
        return new GetScheduleResponse(
                schedule.getScheduleId(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
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
