package com.clearsolutionstesttask.model.dto;

import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class DateRangeDTO {
    @Past(message = "From date must be in the past")
    private LocalDate from;

    @Past(message = "To date must be in the past")
    private LocalDate to;

    @Override
    public String toString() {
        return "{\"from\": \"" + from + "\", \"to\": \"" + to + "\"}";
    }
}
