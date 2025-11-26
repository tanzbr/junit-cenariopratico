package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.user.CustomerDTO;
import me.caua.egiftstore.dto.user.UserDTO;
import me.caua.egiftstore.dto.user.CustomerResponseDTO;
import me.caua.egiftstore.service.CustomerService;
import me.caua.egiftstore.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class CustomerResourceTest {

    @Inject
    CustomerService customerService;
    @Inject
    TestUtils testUtils;

    @Test
    public void createTest() {
        UserDTO userDTO =
                new UserDTO(
                        "Cliente Teste",
                        testUtils.generateRandom(),
                        testUtils.generateRandom(),
                        "clienteteste",
                        true,
                        LocalDate.now()
                );

        CustomerDTO customerDTO =
                new CustomerDTO(
                        true,
                        userDTO
                );

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerDTO)
                .when()
                .post("/customer")
                .then()
                .statusCode(201)
                .body("user.cpf", is(customerDTO.userDTO().cpf()));
    }

    @Test
    public void updateTest() {
        CustomerResponseDTO response = createFakeCustomer("teste-115");

        UserDTO userDTO =
                new UserDTO(
                        "Usuário Teste Editado",
                        "teste-113",
                        "editado1@teste.com",
                        "usertesteeditado",
                        true,
                        LocalDate.now()
                );

        CustomerDTO customerDTO =
                new CustomerDTO(
                        false,
                        userDTO
                );

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerDTO)
                .when()
                .pathParam("id", response.id())
                .put("/customer/{id}")
                .then()
                .statusCode(204);

        customerService.delete(response.id());
    }

    @Test
    public void findAllTest() {
        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .get("/customer")
                .then()
                .statusCode(200);
    }

    @Test
    public void findByIdTest() {
        CustomerResponseDTO response = createFakeCustomer("teste-116");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.id())
                .get("/customer/{id}")
                .then()
                .statusCode(200)
                .body("id", is(response.id().intValue()));

        customerService.delete(response.id());
    }

    @Test
    public void findByNameTest() {
        CustomerResponseDTO response = createFakeCustomer("444.444");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("name", response.user().name())
                .get("/customer/search/name/{name}")
                .then()
                .statusCode(200)
                .body("user.name", hasItem(response.user().name()));

        customerService.delete(response.id());
    }

    @Test
    public void deleteTest() {
        CustomerResponseDTO response = createFakeCustomer("555.555");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.id())
                .delete("/customer/{id}")
                .then()
                .statusCode(204);
    }

    public CustomerResponseDTO createFakeCustomer(String cpf) {
        UserDTO userDTO =
                new UserDTO(
                        "Cliente2 Teste",
                        cpf,
                        "cliente3@teste.com",
                        "cliente2teste",
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
