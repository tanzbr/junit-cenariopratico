package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.order.OrderDTO;
import me.caua.egiftstore.dto.order.OrderItemDTO;
import me.caua.egiftstore.dto.order.OrderResponseDTO;
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
import me.caua.egiftstore.service.CustomerService;
import me.caua.egiftstore.service.GiftCardService;
import me.caua.egiftstore.service.OrderService;
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
class PaymentResourceTest {

    @Inject
    GiftCardService giftCardService;
    @Inject
    OrderService orderService;
    @Inject
    CustomerService customerService;
    @Inject
    TestUtils testUtils;

    @Test
    public void updateTest() {
        OrderResponseDTO orderResponseDTO = orderService.create(createFakeOrder());

        PaymentDTO paymentDTO = new PaymentDTO(20.0, PaymentGateway.MERCADOPAGO, PaymentStatus.REFUNDED);

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentDTO)
                .when()
                .pathParam("id", orderResponseDTO.paymentDTO().id())
                .put("/payment/{id}")
                .then()
                .statusCode(204);

        orderService.delete(orderResponseDTO.id());
    }

    @Test
    public void findAllTest() {
        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .get("/payment")
                .then()
                .statusCode(200);
    }

    @Test
    public void findByIdTest() {
        OrderResponseDTO orderResponseDTO = orderService.create(createFakeOrder());

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", orderResponseDTO.paymentDTO().id())
                .get("/payment/{id}")
                .then()
                .statusCode(200)
                .body("id", is(orderResponseDTO.paymentDTO().id().intValue()));

        orderService.delete(orderResponseDTO.id());
    }

    private OrderDTO createFakeOrder() {
        String identifier = testUtils.generateRandom();
        CustomerResponseDTO customer = createFakeCustomer(identifier);
        GiftCardResponseDTO giftcard = createFakeGiftCard();

        List<OrderItemDTO> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDTO(
                giftcard.price(),
                0.0,
                1,
                giftcard.id()
        ));

        return new OrderDTO(
                LocalDateTime.now(),
                customer.id(),
                orderItems,
                new PaymentDTO(0.0, PaymentGateway.MERCADOPAGO, PaymentStatus.PENDING)
        );
    }

    public CustomerResponseDTO createFakeCustomer(String cpf) {
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

    public GiftCardResponseDTO createFakeGiftCard() {
        String uniqueId = testUtils.generateRandom();
        GiftCompanyResponseDTO giftCompanyResponseDTO = testUtils.createFakeCompany();

        GiftCodeDTO giftCodeDTO =
                new GiftCodeDTO("codigoteste " + uniqueId, GiftState.AVAILABLE, null);
        GiftCodeDTO giftCodeDTO2 =
                new GiftCodeDTO("codigoteste " + uniqueId, GiftState.AVAILABLE, null);


        GiftCardDTO giftCardDTO =
                new GiftCardDTO(
                        "Gift Card Google Teste ",
                        "Gift Card para a Play Store Teste",
                        40.0,
                        giftCompanyResponseDTO.id(),
                        List.of("tag1", "tag2"),
                        List.of(giftCodeDTO, giftCodeDTO2),
                        true
                );

        return giftCardService.create(giftCardDTO);
    }
}
