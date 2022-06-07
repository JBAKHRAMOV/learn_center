package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "group_student")
public class GroupStudentEntity extends BaseEntity {
    @Column(name = "group_id")
    private Long groupId;
    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    @Column(name = "student_id")
    private Long studentId;
    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity student;
}