package me.caua.egiftstore.dto.other;

import jakarta.validation.constraints.NotNull;
import me.caua.egiftstore.dto.user.UserDTO;

public record StringDTO(
        @NotNull(message = "content cannot be null")
        String content
) {

}
