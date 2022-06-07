package com.company.controller;

import com.company.dto.group.ChangeGroupTimeTableDetailDTO;
import com.company.dto.group.GroupTimeTableDTO;
import com.company.service.GroupTimeTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/group-time-table")
@RequiredArgsConstructor
@Slf4j
public class GroupTimeTableController {
    private final GroupTimeTableService groupTimeTableService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupTimeTableDTO> create(@RequestBody @Valid GroupTimeTableDTO dto) {
        return ResponseEntity.ok(groupTimeTableService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(groupTimeTableService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupTimeTableDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupTimeTableService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeGroupTimeTableDetailDTO dto) {
        return ResponseEntity.ok(groupTimeTableService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupTimeTableService.delete(id));
    }
}
