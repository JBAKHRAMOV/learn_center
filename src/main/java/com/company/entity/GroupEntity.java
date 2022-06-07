package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "group_table")
public class GroupEntity extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;

    @Column(name = "course_id")
    private Long courseId;
    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private CourseEntity course;

    @Column(name = "teacher_id")
    private Long teacherId;
    @ManyToOne
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private TeacherEntity teacher;

    @Column(name = "room_id")
    private Long roomId;
    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private RoomEntity room;

    @Column(name = "status_id")
    private Long statusId;
    @OneToOne
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private StatusEntity status;

}