package com.example.scheduleproject.schedule.repository;

import com.example.scheduleproject.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    @Query("SELECT s FROM Schedule s " +
            "LEFT JOIN s.comments " +
            "WHERE s.scheduleId = :scheduleId")
    Optional<Schedule> findByIdWithComments(@Param("scheduleId") Long scheduleId);

    // 작성자명으로 조회 + 수정일 내림차순
    List<Schedule> findAllByUser_UserIdOrderByUpdatedDateDesc(Long userId);

    // 전체 조회 + 수정일 내림차순
    List<Schedule> findAllByOrderByUpdatedDateDesc();

    @Query("SELECT s FROM Schedule s " +
            "JOIN s.user " +
            "ORDER BY s.updatedDate DESC")
    Page<Schedule> findAllWithUserOrderByUpdatedDateDesc(Pageable pageable);
}
