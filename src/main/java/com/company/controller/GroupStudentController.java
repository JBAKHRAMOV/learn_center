package com.company.controller;

import com.company.dto.group.ChangeGroupStudentDetailDTO;
import com.company.dto.group.GroupStudentDTO;
import com.company.service.GroupStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/group-student")
@RequiredArgsConstructor
@Slf4j
public class GroupStudentController {
    private final GroupStudentService groupStudentService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupStudentDTO> create(@RequestBody @Valid GroupStudentDTO dto) {
        return ResponseEntity.ok(groupStudentService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(groupStudentService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupStudentDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupStudentService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeGroupStudentDetailDTO dto) {
        return ResponseEntity.ok(groupStudentService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupStudentService.delete(id));
    }

}
