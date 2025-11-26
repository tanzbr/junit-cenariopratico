package me.caua.egiftstore.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import me.caua.egiftstore.dto.user.EmployeeDTO;
import me.caua.egiftstore.dto.product.GiftCompanyDTO;
import me.caua.egiftstore.dto.user.UserDTO;
import me.caua.egiftstore.dto.product.GiftCompanyResponseDTO;
import me.caua.egiftstore.dto.user.UserResponseDTO;
import me.caua.egiftstore.enums.Role;
import me.caua.egiftstore.repository.EmployeeRepository;
import me.caua.egiftstore.service.EmployeeService;
import me.caua.egiftstore.service.GiftCompanyService;
import me.caua.egiftstore.service.HashService;
import me.caua.egiftstore.service.JwtService;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TestUtils {

    @Inject
    EmployeeService employeeService;
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    HashService hashService;
    @Inject
    JwtService jwtService;
    @Inject
    GiftCompanyService giftCompanyService;

    List<String> cache = new ArrayList<>();

    public String getAuth() {
        UserResponseDTO userResponseDTO = null;

        if (employeeRepository.findByCpf("111-TESTE") != null) {
            userResponseDTO = employeeService.login("tanz@gmail.com", hashService.getHashSenha("admin"));
        } else {
            UserDTO userDTO = new UserDTO(
                    "Darius Tanz",
                    "111-TESTE",
                    "tanz@gmail.com",
                    "admin",
                    false,
                    LocalDate.now()
            );

            userResponseDTO = employeeService.create(new EmployeeDTO(
                    40.0,
                    1200.0,
                    LocalDate.now(),
                    userDTO,
                    Role.CEO
            )).user();
        }

        return jwtService.generateJwt(userResponseDTO, 0);
    }

    public GiftCompanyResponseDTO createFakeCompany() {
        GiftCompanyDTO giftCompanyDTO
                = new GiftCompanyDTO("Teste", "111.111.111");

        return giftCompanyService.create(giftCompanyDTO);
    }

    public String generateRandom() {
        int length = 5;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        while (cache.contains(generatedString)) {
            generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        }
        cache.add(generatedString);

        return "devtest-"+generatedString;
    }

}
