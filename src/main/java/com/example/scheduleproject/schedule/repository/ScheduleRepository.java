package com.example.scheduleproject.schedule.repository;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.schedule.dto.res.GetScheduleDetailResponse;
import com.example.scheduleproject.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    // Schedule DTO 조회 (댓글 제외)
    @Query("""
        SELECT new com.example.scheduleproject.schedule.dto.res.GetScheduleDetailResponse(
            s.scheduleId,
            s.title,
            s.content,
            u.username,
            u.userId,
            s.createdDate,
            s.updatedDate,
            null
        )
        FROM Schedule s
        JOIN s.user u
        WHERE s.scheduleId = :scheduleId
    """)
    Optional<GetScheduleDetailResponse> findScheduleDetail(@Param("scheduleId") Long scheduleId);

    // 댓글 DTO 조회
    @Query("""
        SELECT new com.example.scheduleproject.comment.dto.res.GetCommentResponse(
            c.commentId,
            c.content,
            u.username,
            u.userId,
            s.scheduleId,
            c.createdDate,
            c.updatedDate
        )
        FROM Comment c
        JOIN c.user u
        JOIN c.schedule s
        WHERE s.scheduleId = :scheduleId
        ORDER BY c.createdDate ASC
    """)
    List<GetCommentResponse> findCommentsByScheduleId(@Param("scheduleId") Long scheduleId);

    // 작성자명으로 조회 + 수정일 내림차순
    List<Schedule> findAllByUser_UserIdOrderByUpdatedDateDesc(Long userId);

    // 전체 조회 + 수정일 내림차순
    List<Schedule> findAllByOrderByUpdatedDateDesc();

    @Query("SELECT s FROM Schedule s " +
            "JOIN s.user " +
            "ORDER BY s.updatedDate DESC")
    Page<Schedule> findAllWithUserOrderByUpdatedDateDesc(Pageable pageable);
}
