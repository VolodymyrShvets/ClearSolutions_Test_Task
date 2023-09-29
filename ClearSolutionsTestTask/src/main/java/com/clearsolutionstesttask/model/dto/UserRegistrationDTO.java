package com.clearsolutionstesttask.model.dto;

import com.clearsolutionstesttask.service.emailvalidator.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserRegistrationDTO {
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "First Name is mandatory")
    private String firstName;
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;
    @NotNull(message = "Birth Date is mandatory")
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
