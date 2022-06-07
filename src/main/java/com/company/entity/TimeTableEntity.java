package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "time_table")
public class TimeTableEntity extends BaseEntity {
    @Column
    private String startTime;
    @Column
    private String endTime;

    @Column(name = "day_id")
    private Long dayId;
    @ManyToOne
    @JoinColumn(name = "day_id", insertable = false, updatable = false)
    private DayEntity day;
}