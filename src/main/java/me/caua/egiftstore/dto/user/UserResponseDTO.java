package me.caua.egiftstore.dto.user;

import me.caua.egiftstore.model.User;

import java.time.LocalDate;

public record UserResponseDTO(
        Long id,
        String name,
        String cpf,
        String email,
        Boolean twoFactor,
        LocalDate birthDate
) {
    public static UserResponseDTO valueOf(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getEmail(),
                user.getTwoFactor(),
                user.getBirthDate());
    }
}
