package me.caua.egiftstore.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AuthUserDTO(
        @NotNull(message = "email cannot be null.")
        String email,
        @NotNull(message = "password cannot be null.")
        String password,
        @PositiveOrZero(message = "profile must be 0 or 1")
        int profile
) {



}
