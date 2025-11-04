package com.example.scheduleproject.repository;

import com.example.scheduleproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    // 작성자명으로 조회 + 수정일 내림차순
    List<Schedule> findAllByNameOrderByUpdatedDateDesc(String name);

    // 전체 조회 + 수정일 내림차순
    List<Schedule> findAllByOrderByUpdatedDateDesc();

}
