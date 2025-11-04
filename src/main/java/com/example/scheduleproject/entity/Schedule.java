package com.example.scheduleproject.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name="schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String content;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String password;

    public Schedule(String title, String content, String name, String password) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.password=password;
    }

    public void updateSchedule(String title, String name){
        this.title=title;
        this.name=name;
    }
}
