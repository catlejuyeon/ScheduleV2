package com.example.scheduleproject.comment.entity;

import com.example.scheduleproject.common.entity.BaseEntity;
import com.example.scheduleproject.schedule.entity.Schedule;
import com.example.scheduleproject.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 100,nullable = false)
    private String content;

    //유저 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //스케줄 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id",nullable = false)
    private Schedule schedule;

    public Comment(String content, User user, Schedule schedule) {
        this.content = content;
        this.user = user;
        this.schedule = schedule;
    }
}
