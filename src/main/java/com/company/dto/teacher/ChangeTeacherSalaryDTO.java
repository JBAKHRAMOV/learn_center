package com.company.dto.teacher;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeTeacherSalaryDTO {
    @Positive(message = "salary required")
    private Double salary;
    @NotBlank(message = "phone required")
    private String phone;
}
