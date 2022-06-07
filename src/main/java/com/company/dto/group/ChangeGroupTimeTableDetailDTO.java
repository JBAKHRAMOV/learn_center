package com.company.dto.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeGroupTimeTableDetailDTO {
    @Positive(message = "id required")
    private Long id;
    @Positive(message = "group id required")
    private Long groupId;
    @Positive(message = "time table id required")
    private Long timeTableId;
}
