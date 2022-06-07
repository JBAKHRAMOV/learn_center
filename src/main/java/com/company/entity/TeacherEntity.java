package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class TeacherEntity extends BaseEntity {
    @Column
    private Double salary = 0.0;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}