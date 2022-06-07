package com.company.controller;

import com.company.service.AuthorizationService;
import com.company.dto.AuthDTO;
import com.company.dto.user.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/authorization/")
@Api(tags = "Authorization")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    @ApiOperation(value = "Login", notes = "method for login ")
    public ResponseEntity<UserDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authorizationService.login(dto));
    }
}
