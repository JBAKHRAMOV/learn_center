package com.company.dto.student;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private String firstName;
    private String lastName;
    private String phone;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
