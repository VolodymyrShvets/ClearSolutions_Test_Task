package com.clearsolutionstesttask.model;

import com.clearsolutionstesttask.service.agevalidator.BirthDate;
import com.clearsolutionstesttask.service.emailvalidator.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "my_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @NotNull(message = "Birth Date is mandatory")
    @BirthDate
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ID, user.ID) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(ID, email, firstName, lastName, birthDate, address, phoneNumber);
    }
}
