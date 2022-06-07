package com.company.dto.group;

import com.company.entity.CourseEntity;
import com.company.entity.RoomEntity;
import com.company.entity.StatusEntity;
import com.company.entity.TeacherEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDTO {
    private Long id;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private String name;
    @NotBlank(message = "start date required!")
    private String startDate;
    @NotBlank(message = "end date required!")
    private String endDate;

    @Positive(message = "course id required!")
    private Long courseId;
    private CourseEntity course;

    @Positive(message = "teacher id required!")
    private Long teacherId;
    private TeacherEntity teacher;

    @Positive(message = "room id required!")
    private Long roomId;
    private RoomEntity room;

    @Positive(message = "status id required!")
    private Long statusId;
    private StatusEntity status;
}
