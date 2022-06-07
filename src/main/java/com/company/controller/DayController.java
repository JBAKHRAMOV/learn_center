package com.company.controller;

import com.company.dto.day.ChangeDayDetailDTO;
import com.company.dto.day.DayDTO;
import com.company.service.DayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/day")
@RequiredArgsConstructor
@Slf4j
public class DayController {
    private final DayService dayService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DayDTO> create(@RequestBody @Valid DayDTO dto) {
        log.info("Create day {}" , dto);
        return ResponseEntity.ok(dayService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(dayService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DayDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(dayService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeDayDetailDTO dto) {
        log.info("Update day detail {}", dto);
        return ResponseEntity.ok(dayService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete day {}", id);
        return ResponseEntity.ok(dayService.delete(id));
    }
}
