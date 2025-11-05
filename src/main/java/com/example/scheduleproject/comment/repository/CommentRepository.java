package com.example.scheduleproject.comment.repository;

import com.example.scheduleproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    long countBySchedule_ScheduleId(Long scheduleId);
    List<Comment> findAllBySchedule_ScheduleIdOrderByCreatedDateDesc(Long scheduleId);
}
