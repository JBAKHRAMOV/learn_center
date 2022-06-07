package com.company.dto.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeGroupDetailDTO {
    private Long id;
    @NotBlank(message = "name required")
    private String name;

    @NotBlank(message = "start date required")
    private String startDate;
    @NotBlank(message = "end date required")
    private String endDate;

    @NotBlank(message = "course id required!")
    private Long courseId;

    @NotBlank(message = "teacher id required!")
    private Long teacherId;

    @NotBlank(message = "room id required!")
    private Long roomId;

    @NotBlank(message = "status id required!")
    private Long statusId;
}
