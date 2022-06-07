package com.company.service;

import com.company.dto.registration.RegistrationRequestDTO;
import com.company.enums.UserRole;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.TimeExpiredException;
import com.company.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserService userService;
    private final ConfirmationSmsService confirmationSmsService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public String registerTeacher(RegistrationRequestDTO request) {

        String smsCode = userService.signUpUser(
                new UserEntity(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPhone(),
                        request.getPassword(),
                        UserRole.ROLE_TEACHER
                )
        );

        return "Please confirm this code: " + smsCode;
    }

    public String registerStudent(RegistrationRequestDTO request) {

        String smsCode = userService.signUpUser(
                new UserEntity(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPhone(),
                        request.getPassword(),
                        UserRole.ROLE_STUDENT)
        );

        return "Please confirm this code: " + smsCode;
    }

    @Transactional
    public String confirmSms(String sms) {
        var confirmationSms = confirmationSmsService
                .getSms(sms)
                .orElseThrow(() ->
                        new ItemNotFoundException("sms not found"));

        if (confirmationSms.getConfirmedAt() != null)
            throw new ItemAlreadyExistsException("phone already confirmed");

        var expiredAt = confirmationSms.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new TimeExpiredException("sms expired");

        confirmationSmsService.setConfirmedAt(sms);
        userService.enableUser(
                confirmationSms.getUserEntity().getPhone());

        if (confirmationSms.getUserEntity().getRole().equals(UserRole.ROLE_TEACHER))
            teacherService.saveTeacher(confirmationSms.getUserEntity());
        if (confirmationSms.getUserEntity().getRole().equals(UserRole.ROLE_STUDENT))
            studentService.saveStudent(confirmationSms.getUserEntity());

        confirmationSmsService.delete(confirmationSms);

        return "confirmed successfully!";
    }
}
