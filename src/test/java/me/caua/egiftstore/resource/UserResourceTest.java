package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.auth.AuthUserDTO;
import me.caua.egiftstore.dto.order.OrderDTO;
import me.caua.egiftstore.dto.order.OrderItemDTO;
import me.caua.egiftstore.dto.order.OrderResponseDTO;
import me.caua.egiftstore.dto.other.StringDTO;
import me.caua.egiftstore.dto.payment.PaymentDTO;
import me.caua.egiftstore.dto.product.GiftCardDTO;
import me.caua.egiftstore.dto.product.GiftCardResponseDTO;
import me.caua.egiftstore.dto.product.GiftCodeDTO;
import me.caua.egiftstore.dto.product.GiftCompanyResponseDTO;
import me.caua.egiftstore.dto.user.CustomerDTO;
import me.caua.egiftstore.dto.user.CustomerResponseDTO;
import me.caua.egiftstore.dto.user.UserDTO;
import me.caua.egiftstore.enums.GiftState;
import me.caua.egiftstore.enums.PaymentGateway;
import me.caua.egiftstore.enums.PaymentStatus;
import me.caua.egiftstore.service.*;
import me.caua.egiftstore.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class UserResourceTest {

    @Inject
    CustomerService customerService;
    @Inject
    JwtService jwtService;
    @Inject
    TestUtils testUtils;

    @Test
    public void updateNameTest() {

        CustomerResponseDTO customerResponseDTO = createFakeCustomer();

        given()
                .header("Authorization", "Bearer " + jwtService.generateJwt(customerResponseDTO.user(), 1))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StringDTO("test name very fun"))
                .when()
                .patch("/user/update/name")
                .then()
                .statusCode(204);

        customerService.delete(customerResponseDTO.id());
    }

    @Test
    public void updatePassTest() {

        CustomerResponseDTO customerResponseDTO = createFakeCustomer();

        given()
                .header("Authorization", "Bearer " + jwtService.generateJwt(customerResponseDTO.user(), 1))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StringDTO("testpass"))
                .when()
                .patch("/user/update/password")
                .then()
                .statusCode(204);

        customerService.delete(customerResponseDTO.id());
    }

    @Test
    public void updateEmailTest() {

        CustomerResponseDTO customerResponseDTO = createFakeCustomer();

        given()
                .header("Authorization", "Bearer " + jwtService.generateJwt(customerResponseDTO.user(), 1))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StringDTO("test email"))
                .when()
                .patch("/user/update/email")
                .then()
                .statusCode(204);

        customerService.delete(customerResponseDTO.id());
    }

    public CustomerResponseDTO createFakeCustomer() {
        String identifier = testUtils.generateRandom();
        UserDTO userDTO =
                new UserDTO(
                        "Cliente2 Teste",
                        identifier,
                        "email-"+identifier+"@teste.com",
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
