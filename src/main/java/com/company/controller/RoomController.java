package com.company.controller;

import com.company.dto.room.ChangeRoomDetailDTO;
import com.company.dto.room.RoomDTO;
import com.company.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/room")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDTO> create(@RequestBody @Valid RoomDTO dto) {
        log.info("Create room {}" , dto);
        return ResponseEntity.ok(roomService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(roomService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangeRoomDetailDTO dto) {
        log.info("Update room detail {}", dto);
        return ResponseEntity.ok(roomService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete room {}", id);
        return ResponseEntity.ok(roomService.delete(id));
    }
}
