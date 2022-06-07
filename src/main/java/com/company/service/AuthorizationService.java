package com.company.service;

import com.company.dto.AuthDTO;
import com.company.dto.user.UserDTO;
import com.company.exceptions.AppBadRequestException;
import com.company.repository.UserRepository;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService {
    private final UserRepository userRepository;

    public UserDTO login(AuthDTO dto) {
        var encoder = new BCryptPasswordEncoder();

        var profile = userRepository
                .findByPhone(dto.getUsername())
                .orElseThrow(() -> new AppBadRequestException("Login or Password not valid."));

        if (!encoder.matches(dto.getPassword(), profile.getPassword()))
            throw new AppBadRequestException("Login or Password not valid");

        var jwt = JwtUtil.createJwt(profile.getId(), profile.getUsername());

        var user = new UserDTO();
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setUsername(profile.getUsername());
        user.setJwt(jwt);

        return user;
    }
}
