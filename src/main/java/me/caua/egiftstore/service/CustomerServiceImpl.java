package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.user.CustomerDTO;
import me.caua.egiftstore.dto.user.CustomerResponseDTO;
import me.caua.egiftstore.dto.user.UserResponseDTO;
import me.caua.egiftstore.model.Customer;
import me.caua.egiftstore.model.User;
import me.caua.egiftstore.repository.CustomerRepository;
import me.caua.egiftstore.repository.UserRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {

    @Inject
    public CustomerRepository customerRepository;
    @Inject
    public UserRepository userRepository;
    @Inject
    public HashService hashService;
    @Override
    @Transactional
    public CustomerResponseDTO create(@Valid CustomerDTO customerDTO) {
        validateCustomerExistsByCpf(customerDTO.userDTO().cpf());
        validateCustomerExistsByEmail(customerDTO.userDTO().email());

        Customer customer = new Customer();
        customer.setAcceptMarketing(customerDTO.acceptMarketing());

        User user;
        if (checkUserNotExists(customerDTO.userDTO().cpf())) {
            user = new User();
        } else {
            user = userRepository.findByCpf(customerDTO.userDTO().cpf());
            user.setDataAlteracao(LocalDateTime.now());
        }
        user.setName(customerDTO.userDTO().name());
        user.setCpf(customerDTO.userDTO().cpf());
        user.setEmail(customerDTO.userDTO().email());
        user.setPassword(hashService.getHashSenha(customerDTO.userDTO().password()));
        user.setBirthDate(customerDTO.userDTO().birthDate());
        user.setTwoFactor(customerDTO.userDTO().twoFactor());
        customer.setUser(user);

        customerRepository.persist(customer);

        return CustomerResponseDTO.valueOf(customer);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid CustomerDTO customerDTO) {
        validateCustomerExists(id);

        Customer customer = customerRepository.findById(id);
        customer.setAcceptMarketing(customerDTO.acceptMarketing());

        User user;
        if (checkUserNotExists(customerDTO.userDTO().cpf())) {
            user = new User();
        } else {
            user = userRepository.findByCpf(customerDTO.userDTO().cpf());
            user.setDataAlteracao(LocalDateTime.now());
        }
        user.setName(customerDTO.userDTO().name());
        user.setCpf(customerDTO.userDTO().cpf());
        user.setEmail(customerDTO.userDTO().email());
        user.setPassword(hashService.getHashSenha(customerDTO.userDTO().password()));
        user.setBirthDate(customerDTO.userDTO().birthDate());
        user.setTwoFactor(customerDTO.userDTO().twoFactor());
        customer.setUser(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateCustomerExists(id);
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponseDTO findById(Long id) {
        validateCustomerExists(id);
        return CustomerResponseDTO.valueOf(customerRepository.findById(id));
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
        return customerRepository.listAll()
                .stream()
                .map(CustomerResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<CustomerResponseDTO> findByName(String name) {
        return customerRepository.findByName(name)
                .stream()
                .map(CustomerResponseDTO::valueOf)
                .toList();
    }

    public UserResponseDTO login(String email, String password) {
        if (customerRepository.findByEmailAndPass(email, password) == null)
            throw new ValidationException("email and password", "customer not found");
        return UserResponseDTO.valueOf(customerRepository.findByEmailAndPass(email, password).getUser());
    }

    public void validateCustomerExists(Long id) {
        if (customerRepository.findById(id) == null)
            throw new ValidationException("id", "customer not found");
    }

    public void validateCustomerExistsByCpf(String cpf) {
        if (customerRepository.findByCpf(cpf) != null)
            throw new ValidationException("cpf", "a Customer with this cpf already exists.");
    }

    public void validateCustomerExistsByEmail(String email) {
        if (customerRepository.findByEmail(email) != null)
            throw new ValidationException("cpf", "a Customer with this email already exists.");
    }

    public boolean checkUserNotExists(String cpf) {
        return userRepository.findByCpf(cpf) == null;
    }

}
