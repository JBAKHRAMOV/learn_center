package com.company.dto.time;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeTimeTableDetailDTO {
    @Positive(message = "id required")
    private Long id;
    @NotBlank(message = "start time required")
    private String startTime;
    @NotBlank(message = "end time required")
    private String endTime;
}