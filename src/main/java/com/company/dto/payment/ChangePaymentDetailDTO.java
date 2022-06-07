package com.company.dto.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePaymentDetailDTO {
    @Positive(message = "id required")
    private Long id;
    @Positive(message = "student id required")
    private Long studentId;
    @Positive(message = "payment type id required")
    private Long paymentTypeId;
    @Positive(message = "sum required required")
    private Double sum;
}
