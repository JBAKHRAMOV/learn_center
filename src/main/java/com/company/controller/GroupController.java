package com.company.controller;

import com.company.dto.group.ChangeGroupDetailDTO;
import com.company.dto.group.GroupDTO;
import com.company.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDTO> create(@RequestBody @Valid GroupDTO dto) {
        return ResponseEntity.ok(groupService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(groupService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeGroupDetailDTO dto) {
        return ResponseEntity.ok(groupService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupService.delete(id));
    }
}
