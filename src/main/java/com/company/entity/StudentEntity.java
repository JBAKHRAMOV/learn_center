package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}