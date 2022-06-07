package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {
    @Column
    private Double sum;
    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "student_id")
    private Long studentId;
    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity student;

    @Column(name = "payment_type_id")
    private Long paymentTypeId;
    @ManyToOne
    @JoinColumn(name = "payment_type_id", insertable = false, updatable = false)
    private PaymentTypeEntity paymentType;
}