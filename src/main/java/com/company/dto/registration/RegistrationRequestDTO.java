package com.company.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RegistrationRequestDTO {
    @NotBlank(message = "firstname required!")
    @Size(min = 3, max = 255, message = "entered information must be more than 3 letters and less than 255 letters!")
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
}
