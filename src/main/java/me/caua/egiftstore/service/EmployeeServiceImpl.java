package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.user.EmployeeDTO;
import me.caua.egiftstore.dto.user.EmployeeResponseDTO;
import me.caua.egiftstore.dto.user.UserResponseDTO;
import me.caua.egiftstore.model.Employee;
import me.caua.egiftstore.model.User;
import me.caua.egiftstore.repository.EmployeeRepository;
import me.caua.egiftstore.repository.UserRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    public EmployeeRepository employeeRepository;
    @Inject
    public UserRepository userRepository;
    @Inject
    public HashService hashService;
    @Override
    @Transactional
    public EmployeeResponseDTO create(@Valid EmployeeDTO employeeDTO) {
        validateEmployeeExistsByCpf(employeeDTO.user().cpf());

        Employee employee = new Employee();
        employee.setSalary(employeeDTO.salary());
        employee.setRole(employeeDTO.role());
        employee.setContractDate(employeeDTO.contractDate());
        employee.setWeeklyHours(employeeDTO.weeklyHours());

        User user;
        if (checkUserNotExists(employeeDTO.user().cpf())) {
            user = new User();
        } else {
            user = userRepository.findByCpf(employeeDTO.user().cpf());
            user.setDataAlteracao(LocalDateTime.now());
        }
        user.setName(employeeDTO.user().name());
        user.setCpf(employeeDTO.user().cpf());
        user.setEmail(employeeDTO.user().email());
        user.setPassword(hashService.getHashSenha(employeeDTO.user().password()));
        user.setBirthDate(employeeDTO.user().birthDate());
        user.setTwoFactor(employeeDTO.user().twoFactor());
        employee.setUser(user);

        employeeRepository.persist(employee);

        return EmployeeResponseDTO.valueOf(employee);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid EmployeeDTO employeeDTO) {
        validateEmployeeExists(id);

        Employee employee = employeeRepository.findById(id);
        employee.setSalary(employeeDTO.salary());
        employee.setRole(employeeDTO.role());
        employee.setContractDate(employeeDTO.contractDate());
        employee.setWeeklyHours(employeeDTO.weeklyHours());

        User user;
        if (checkUserNotExists(employeeDTO.user().cpf())) {
            user = new User();
        } else {
            user = userRepository.findByCpf(employeeDTO.user().cpf());
        }
        user.setName(employeeDTO.user().name());
        user.setCpf(employeeDTO.user().cpf());
        user.setEmail(employeeDTO.user().email());
        user.setPassword(hashService.getHashSenha(employeeDTO.user().password()));
        user.setBirthDate(employeeDTO.user().birthDate());
        user.setTwoFactor(employeeDTO.user().twoFactor());
        employee.setUser(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateEmployeeExists(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponseDTO findById(Long id) {
        validateEmployeeExists(id);
        return EmployeeResponseDTO.valueOf(employeeRepository.findById(id));
    }

    @Override
    public EmployeeResponseDTO findByCpf(String cpf) {
        validateEmployeeNotExistsByCpf(cpf);
        return EmployeeResponseDTO.valueOf(employeeRepository.findByCpf(cpf));
    }

    @Override
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.listAll()
                .stream()
                .map(EmployeeResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<EmployeeResponseDTO> findByName(String name) {
        return employeeRepository.findByName(name)
                .stream()
                .map(EmployeeResponseDTO::valueOf)
                .toList();
    }

    public UserResponseDTO login(String email, String password) {
        if (employeeRepository.findByEmailAndPass(email, password) == null)
            throw new ValidationException("email and password", "employee not found");
        return UserResponseDTO.valueOf(employeeRepository.findByEmailAndPass(email, password).getUser());
    }

    public void validateEmployeeExists(Long id) {
        if (employeeRepository.findById(id) == null)
            throw new ValidationException("id", "employee not found.");
    }

    public void validateEmployeeExistsByCpf(String cpf) {
        if (employeeRepository.findByCpf(cpf) != null)
            throw new ValidationException("cpf", "an employee with this email already exists.");
    }

    public void validateEmployeeNotExistsByCpf(String cpf) {
        if (employeeRepository.findByCpf(cpf) == null)
            throw new ValidationException("cpf", "employee not found");
    }

    public void validateEmployeeNotExistsByEmail(String email) {
        if (employeeRepository.findByEmail(email) == null)
            throw new ValidationException("cpf", "employee not found");
    }

    public boolean checkUserNotExists(String cpf) {
        return userRepository.findByCpf(cpf) == null;
    }

}
