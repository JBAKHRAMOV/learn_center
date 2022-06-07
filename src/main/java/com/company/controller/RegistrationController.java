package com.company.controller;

import com.company.dto.registration.RegistrationRequestDTO;
import com.company.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
@Api(tags = "registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/teacher")
    @ApiOperation(value = "teacher registration", notes = "method for teacher registration")
    public ResponseEntity<?> registerTeacher(@RequestBody @Valid RegistrationRequestDTO request) {

        return ResponseEntity.ok(registrationService.registerTeacher(request));
    }

    @PostMapping("/student")
    @ApiOperation(value = "student registration", notes = "method for student registration")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequestDTO request) {
        return ResponseEntity.ok(registrationService.registerStudent(request));
    }

    @GetMapping(path = "confirm")
    @ApiOperation(value = "sms confirm", notes = "method for sms confirm")
    public ResponseEntity<?> confirm(@RequestParam("sms") String sms) {
        return ResponseEntity.ok(registrationService.confirmSms(sms));
    }
}
