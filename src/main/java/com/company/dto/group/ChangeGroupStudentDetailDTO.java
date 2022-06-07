package com.company.dto.group;

import com.company.entity.GroupEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeGroupStudentDetailDTO {
    @Positive(message = "id required")
    private Long id;
    @Positive(message = "group id required")
    private Long groupId;
    private GroupEntity group;
    @Positive(message = "student id required")
    private Long studentId;
}
