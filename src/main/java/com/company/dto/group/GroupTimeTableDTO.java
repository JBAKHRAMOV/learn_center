package com.company.dto.group;

import com.company.entity.GroupEntity;
import com.company.entity.TimeTableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupTimeTableDTO {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    @Positive(message = "group id required")
    private Long groupId;
    private GroupEntity group;
    @Positive(message = "time table id required")
    private Long timeTableId;
    private TimeTableEntity timeTable;
}
