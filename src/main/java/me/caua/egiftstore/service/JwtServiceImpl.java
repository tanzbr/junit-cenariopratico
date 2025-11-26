package me.caua.egiftstore.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import me.caua.egiftstore.dto.user.UserResponseDTO;
import me.caua.egiftstore.model.Employee;
import me.caua.egiftstore.repository.EmployeeRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    @Inject
    public EmployeeRepository employeeRepository;

    private static final Duration EXPIRATION_TIME = Duration.ofDays(7);

    @Override
    public String generateJwt(UserResponseDTO user, int type) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<>();

        // 0 = employee
        if (type == 0) {
            Employee emp = employeeRepository.findByCpf(user.cpf());
            roles.add(emp.getRole().name());

            // 1 = customer
        } else if (type == 1) {
            roles.add("CUSTOMER");
        }

        return Jwt.issuer("unitins-jwt")
                .subject(user.email())
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();
    }
}
