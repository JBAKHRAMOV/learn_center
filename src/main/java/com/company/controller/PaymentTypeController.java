package com.company.controller;

import com.company.dto.payment.ChangePaymentTypeDetailDTO;
import com.company.dto.payment.PaymentTypeDTO;
import com.company.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/payment-type")
@RequiredArgsConstructor
@Slf4j
public class PaymentTypeController {
    private final PaymentTypeService paymentTypeService;

    /**
     * ROLE_ADMIN PERMISSION
     **/

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentTypeDTO> create(@RequestBody @Valid PaymentTypeDTO dto) {
        log.info("Create payment type {}" , dto);
        return ResponseEntity.ok(paymentTypeService.create(dto));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(paymentTypeService.paginationList(page, size));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentTypeDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentTypeService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid ChangePaymentTypeDetailDTO dto) {
        log.info("Update payment type detail {}", dto);
        return ResponseEntity.ok(paymentTypeService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete payment type {}", id);
        return ResponseEntity.ok(paymentTypeService.delete(id));
    }
}
