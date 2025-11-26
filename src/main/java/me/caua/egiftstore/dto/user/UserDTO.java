package me.caua.egiftstore.dto.user;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDTO(
        @NotBlank(message = "name cannot be null or empty.")
        String name,
        @NotBlank(message = "cpf cannot be null or empty.")
        String cpf,
        @Email(message = "email needs to be an valid email.")
        String email,
        @NotBlank(message = "password cannot be null or empty.") @Size(message = "the password must be at least 6 characters long")
        String password,
        @NotNull(message = "twofactor cannot be null")
        Boolean twoFactor,
        @NotNull(message = "birthDate cannot be null") @Past(message = "birthDate cannot be a future date")
        LocalDate birthDate
) {

}
