package com.company.service;

import com.company.entity.UserEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.entity.ConfirmationSmsEntity;
import com.company.repository.UserRepository;
import com.company.service.ConfirmationSmsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "user with phone %s not found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final ConfirmationSmsService confirmationSmsService;

    @Override
    public UserDetails loadUserByUsername(String phone)
            throws UsernameNotFoundException {
        return userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, phone)
                        ));
    }

    public String signUpUser(UserEntity userEntity) {
        var optional = userRepository.findByPhone(userEntity.getPhone());

        if (optional.isPresent())
            throw new ItemAlreadyExistsException("phone number already exists!");

        var encodedPassword = bCryptPasswordEncoder
                .encode(userEntity.getPassword());

        userEntity.setPassword(encodedPassword);

        userRepository.save(userEntity);

        var sms = getRandomNumberString();

        var smsOptional = confirmationSmsService.getSms(sms);
        smsOptional.ifPresent(confirmationSmsService::delete);

        var confirmationSms = new ConfirmationSmsEntity(
                sms,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(2),
                userEntity
        );
        confirmationSmsService.confirmationSms(confirmationSms);

        // TODO: an SMS code must be sent to the phone number

        return sms;
    }

    public void enableUser(String phone) {
        userRepository.enableUser(phone);
    }

    public UserEntity updateUserDetail(String firstName, String lastName,
                                       String phone) {
        var user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new ItemNotFoundException("user not found!"));
        userRepository.updateUserDetail(firstName, lastName, phone);

        return user;
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public UserEntity getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(() -> new ItemNotFoundException("user not found"));
    }
    private String getRandomNumberString() {

        var rnd = new Random();
        int number = rnd.nextInt(99999);

        return String.format("%05d", number);
    }
}
