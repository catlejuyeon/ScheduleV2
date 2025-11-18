package com.example.scheduleproject.schedule.entity;

import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.common.entity.BaseEntity;
import com.example.scheduleproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name="schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updateSchedule(String title){
        this.title=title;
    }
}
