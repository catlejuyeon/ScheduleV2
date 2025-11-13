package com.example.scheduleproject.comment.repository;

import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findBySchedule(Schedule schedule);
}
