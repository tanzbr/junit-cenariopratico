package me.caua.egiftstore.dto.user;

import jakarta.validation.constraints.NotNull;

public record CustomerDTO(
        @NotNull(message = "acceptMarketing cannot be null.")
        Boolean acceptMarketing,
        @NotNull(message = "User cannot be null.")
        UserDTO userDTO
) {

}
