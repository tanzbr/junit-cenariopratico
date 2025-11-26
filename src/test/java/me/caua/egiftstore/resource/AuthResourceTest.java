package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.auth.AuthUserDTO;
import me.caua.egiftstore.dto.user.*;
import me.caua.egiftstore.enums.Role;
import me.caua.egiftstore.service.CustomerService;
import me.caua.egiftstore.service.EmployeeService;
import me.caua.egiftstore.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AuthResourceTest {

    @Inject
    EmployeeService employeeService;
    @Inject
    TestUtils testUtils;
    @Inject
    CustomerService customerService;

    @Test
    public void authTestEmployee() {

        long id = createEmployee().id();

        AuthUserDTO authUserDTO = new AuthUserDTO(
                "auth@teste.com",
                "senhateste",
                0
        );

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authUserDTO)
                .when()
                .post("/auth")
                .then()
                .statusCode(200);

        employeeService.delete(id);
    }

    private EmployeeResponseDTO createEmployee() {
        UserDTO userDTO =
                new UserDTO(
                        "Usuário Teste",
                        "teste-111",
                        "auth@teste.com",
                        "senhateste",
                        true,
                        LocalDate.now()
                );

        EmployeeDTO employeeDTO =
                new EmployeeDTO(
                        40.0,
                        1230.0,
                        LocalDate.now(),
                        userDTO,
                        Role.SALES
                );

        return employeeService.create(employeeDTO);
    }

    public CustomerResponseDTO createCustomer() {
        UserDTO userDTO =
                new UserDTO(
                        "Cliente2 Teste",
                        testUtils.generateRandom(),
                        "auth22@teste.com",
                        "senhateste2",
                        true,
                        LocalDate.now()
                );

        CustomerDTO customerDTO = new CustomerDTO(
                true,
                userDTO
        );

        return customerService.create(customerDTO);
    }
}
