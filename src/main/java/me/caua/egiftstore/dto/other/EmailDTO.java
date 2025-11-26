package me.caua.egiftstore.dto.other;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailDTO(
        @Email(message = "content needs to be a valid email")
        @NotNull(message = "content cannot be null")
        String content
) {

}
