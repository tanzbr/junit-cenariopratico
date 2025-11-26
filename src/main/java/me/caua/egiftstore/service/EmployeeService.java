package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.user.EmployeeDTO;
import me.caua.egiftstore.dto.user.EmployeeResponseDTO;
import me.caua.egiftstore.dto.user.UserResponseDTO;

import java.util.List;

@ApplicationScoped
public interface EmployeeService {

    EmployeeResponseDTO create(@Valid EmployeeDTO employeeDTO);
    void update(Long id, @Valid EmployeeDTO employeeDTO);
    void delete(Long id);
    EmployeeResponseDTO findById(Long id);
    EmployeeResponseDTO findByCpf(String cpf);
    List<EmployeeResponseDTO> findByName(String name);
    List<EmployeeResponseDTO> findAll();
    UserResponseDTO login(String email, String password);

}
