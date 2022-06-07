package com.company.dto.payment;

import com.company.entity.PaymentTypeEntity;
import com.company.entity.StudentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Positive(message = "sum required!")
    private Double sum;
    @NotBlank(message = "description required!")
    private String description;
    @Positive(message = "student id required!")
    private Long studentId;
    @Positive(message = "payment type id required!")
    private Long paymentTypeId;

    private StudentEntity student;
    private PaymentTypeEntity paymentType;
}
