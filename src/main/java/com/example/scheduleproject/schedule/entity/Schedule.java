package com.example.scheduleproject.schedule.entity;

import com.example.scheduleproject.comment.entity.Comment;
import com.example.scheduleproject.common.entity.BaseEntity;
import com.example.scheduleproject.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name="schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String content;
    @Column(length = 20, nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(String title, String content, User user, String password) {
        this.title = title;
        this.content = content;
        this.password=password;
        this.user = user;
    }

    public void updateSchedule(String title){
        this.title=title;
    }

    @OneToMany(mappedBy = "schedule",cascade= CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
