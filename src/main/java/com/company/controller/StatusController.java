package com.company.controller;

import com.company.dto.status.ChangeStatusDetailDTO;
import com.company.dto.status.StatusDTO;
import com.company.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/status")
@RequiredArgsConstructor
@Slf4j
public class StatusController {
    private final StatusService statusService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatusDTO> create(@RequestBody @Valid StatusDTO dto) {
        log.info("Create status {}" , dto);
        return ResponseEntity.ok(statusService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(statusService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatusDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(statusService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeStatusDetailDTO dto) {
        log.info("Update status detail {}", dto);
        return ResponseEntity.ok(statusService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete status {}", id);
        return ResponseEntity.ok(statusService.delete(id));
    }
}
