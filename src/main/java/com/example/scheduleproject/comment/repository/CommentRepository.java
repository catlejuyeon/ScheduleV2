package com.example.scheduleproject.comment.repository;

import com.example.scheduleproject.comment.dto.res.GetCommentResponse;
import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //댓글 조회 (CommentRepository에서 전담)
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
        ORDER BY c.createdDate DESC
    """)
    List<GetCommentResponse> findCommentsByScheduleIdAsDto(@Param("scheduleId") Long scheduleId);

    //댓글개수조회
    long countBySchedule_ScheduleId(Long scheduleId);

    //기본 조회
    List<Comment> findBySchedule(Schedule schedule);
}
