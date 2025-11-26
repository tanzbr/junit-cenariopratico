package me.caua.egiftstore.dto.user;

import me.caua.egiftstore.enums.Role;
import me.caua.egiftstore.model.Employee;

import java.time.LocalDate;

public record EmployeeResponseDTO(
        Long id,
        Double weeklyHours,
        Double salary,
        LocalDate contractDate,
        UserResponseDTO user,
        Role role
) {
    public static EmployeeResponseDTO valueOf(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getWeeklyHours(),
                employee.getSalary(),
                employee.getContractDate(),
                UserResponseDTO.valueOf(employee.getUser()),
                employee.getRole());
    }

}
