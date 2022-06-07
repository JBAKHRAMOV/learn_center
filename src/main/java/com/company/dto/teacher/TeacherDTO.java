package com.company.dto.teacher;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private Double salary;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
