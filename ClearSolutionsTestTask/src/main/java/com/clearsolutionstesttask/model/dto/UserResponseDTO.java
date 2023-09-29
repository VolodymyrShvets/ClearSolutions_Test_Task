package com.clearsolutionstesttask.model.dto;

import com.clearsolutionstesttask.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class UserResponseDTO {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO user = (UserResponseDTO) o;
        return
                Objects.equals(email, user.email) &&
                        Objects.equals(firstName, user.firstName) &&
                        Objects.equals(lastName, user.lastName) &&
                        Objects.equals(birthDate, user.birthDate) &&
                        Objects.equals(address, user.address) &&
                        Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(email, firstName, lastName, birthDate, address, phoneNumber);
    }
}
