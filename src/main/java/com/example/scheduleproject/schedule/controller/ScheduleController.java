package com.example.scheduleproject.schedule.controller;

import com.example.scheduleproject.schedule.dto.req.CreateScheduleRequest;
import com.example.scheduleproject.schedule.dto.req.DeleteScheduleRequest;
import com.example.scheduleproject.schedule.dto.req.UpdateScheduleRequest;
import com.example.scheduleproject.schedule.dto.res.CreateScheduleResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleDetailResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleResponse;
import com.example.scheduleproject.schedule.dto.res.UpdateScheduleResponse;
import com.example.scheduleproject.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleDetailResponse>getOneSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @GetMapping("/schedules")
    public List<GetScheduleResponse> getSchedules(@RequestParam(required = false) Long userId) {
        return scheduleService.findAll(userId);
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId,request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody DeleteScheduleRequest request) {
        scheduleService.delete(scheduleId,request.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
