package com.example.scheduleproject.comment.entity;

import com.example.scheduleproject.common.entity.BaseEntity;
import com.example.scheduleproject.schedule.entity.Schedule;
import jakarta.persistence.*;
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
    @Column(length = 20,nullable = false)
    private String commentAuthor;
    @Column(length = 100,nullable = false)
    private String password;

    //오류나는거같으니 유의
    @ManyToOne
    @JoinColumn(name="schedule_id",nullable = false)
    private Schedule schedule;

    public Comment(String commentAuthor, String content, String password, Schedule schedule) {
        this.commentAuthor = commentAuthor;
        this.content = content;
        this.password = password;
        this.schedule = schedule;
    }
}
