package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "group_time_table")
public class GroupTimeTableEntity extends BaseEntity {

    @Column(name = "group_id")
    private Long groupId;
    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    @Column(name = "time_table_id")
    private Long timeTableId;
    @ManyToOne
    @JoinColumn(name = "time_table_id", insertable = false, updatable = false)
    private TimeTableEntity timeTable;
}