package com.company.service;

import com.company.entity.ConfirmationSmsEntity;
import com.company.repository.ConfirmationSmsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationSmsService {
    private final ConfirmationSmsRepository confirmationSmsRepository;

    public void confirmationSms(ConfirmationSmsEntity sms) {
        confirmationSmsRepository.save(sms);
    }

    public Optional<ConfirmationSmsEntity> getSms(String sms) {
        return confirmationSmsRepository.findBySms(sms);
    }

    public void setConfirmedAt(String sms) {
        confirmationSmsRepository.updateConfirmedAt(LocalDateTime.now(), sms);
    }

    public void delete(ConfirmationSmsEntity sms) {
        confirmationSmsRepository.delete(sms);
    }

}
