package com.company.controller;

import com.company.dto.course.ChangeCourseDetailDTO;
import com.company.dto.course.CourseDTO;
import com.company.service.CourseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "course")
public class CourseController {
    private final CourseService courseService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> create (@RequestBody @Valid CourseDTO dto){
        log.info("Create Course {}", dto);
        return ResponseEntity.ok(courseService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(courseService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeCourseDetailDTO dto){
        log.info("Update course detail {}", dto);
        return ResponseEntity.ok(courseService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        log.info("Delete course {}", id);
        return ResponseEntity.ok(courseService.delete(id));
    }
}
