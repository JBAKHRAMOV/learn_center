package com.company.controller;

import com.company.dto.student.StudentDTO;
import com.company.dto.user.UserDetailDTO;
import com.company.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;

    /**
     * STUDENT PERMISSION
     **/

    @PutMapping("/update-detail")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateDetail(@RequestBody @Valid UserDetailDTO dto,
                                          Authentication authentication) {
        log.info("Update user detail {}", dto);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(studentService.updateStudentDetail(dto, userDetails.getUsername()));
    }

    /**
     * ADMIN PERMISSION
     **/

    @GetMapping("/get-student/{phone}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> getTeacherByPhone(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(studentService.getStudentByPhone(phone));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(studentService.paginationList(page, size));
    }

    @DeleteMapping(path = "delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        log.info("Delete student {}", id);
        return ResponseEntity.ok(studentService.delete(id));
    }
}
