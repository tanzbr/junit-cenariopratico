package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.user.CustomerDTO;
import me.caua.egiftstore.dto.user.CustomerResponseDTO;
import me.caua.egiftstore.dto.user.UserResponseDTO;

import java.util.List;

@ApplicationScoped
public interface CustomerService {

    CustomerResponseDTO create(@Valid CustomerDTO customerDTO);
    void update(Long id, @Valid CustomerDTO customerDTO);
    void delete(Long id);
    CustomerResponseDTO findById(Long id);
    List<CustomerResponseDTO> findByName(String name);
    List<CustomerResponseDTO> findAll();
    UserResponseDTO login(String email, String password);

}
