package com.company.controller;

import com.company.dto.payment.ChangePaymentDetailDTO;
import com.company.dto.payment.PaymentDTO;
import com.company.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid PaymentDTO dto) {
        log.info("Create payment {}" , dto);
        return ResponseEntity.ok(paymentService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(paymentService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangePaymentDetailDTO dto) {
        log.info("Update payment detail {}", dto);
        return ResponseEntity.ok(paymentService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete payment {}", id);
        return ResponseEntity.ok(paymentService.delete(id));
    }
}
