package me.caua.egiftstore.dto.user;

import jakarta.validation.constraints.NotNull;
import me.caua.egiftstore.model.Customer;

public record CustomerResponseDTO(
        Long id,
        @NotNull(message = "acceptMarketing cannot be null.")
        Boolean acceptMarketing,
        @NotNull(message = "User cannot be null.")
        UserResponseDTO user
) {
        public static CustomerResponseDTO valueOf(Customer customer) {
                return new CustomerResponseDTO(
                        customer.getId(),
                        customer.getAcceptMarketing(),
                        UserResponseDTO.valueOf(customer.getUser()));
        }
}
