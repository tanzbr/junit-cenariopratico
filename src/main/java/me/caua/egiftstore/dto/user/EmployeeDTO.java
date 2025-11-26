package me.caua.egiftstore.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.caua.egiftstore.enums.Role;

import java.time.LocalDate;

public record EmployeeDTO(
        @NotNull(message = "weeklyHours cannot be null.") @Positive(message = "weeklyHours cannot be negative or zero.")
        Double weeklyHours,
        @NotNull(message = "salary cannot be null.")
        Double salary,
        @NotNull(message = "contractDate cannot be null.")
        LocalDate contractDate,
        @NotNull(message = "user cannot be null.")
        UserDTO user,
        @NotNull(message = "role cannot be null.")
        Role role
) {

}
