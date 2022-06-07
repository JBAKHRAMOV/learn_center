package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "status")
public class StatusEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;
}