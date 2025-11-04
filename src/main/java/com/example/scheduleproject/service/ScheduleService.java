package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.CreateScheduleRequest;
import com.example.scheduleproject.dto.CreateScheduleResponse;
import com.example.scheduleproject.dto.GetScheduleResponse;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.repository.ScheduleRepository;
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
                savedSchedule.getSchedule_id(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getName(),
                savedSchedule.getPassword(),
                savedSchedule.getCreated_date()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new IllegalStateException("없는 스케줄입니다.")
        );
        return new GetScheduleResponse(
                schedule.getSchedule_id(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getCreated_date(),
                schedule.getUpdated_date()
        );
    }

//    @Transactional(readOnly = true)
//    public List<GetScheduleResponse> findAll(String name) {
//        List<Schedule> schedules;
//
//        if(name!=null&&!name.isBlank()){}
//    }
}
