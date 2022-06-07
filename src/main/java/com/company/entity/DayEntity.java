package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "day")
public class DayEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}