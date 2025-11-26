package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.product.GiftCardDTO;
import me.caua.egiftstore.dto.product.GiftCodeDTO;
import me.caua.egiftstore.dto.product.GiftCardResponseDTO;
import me.caua.egiftstore.dto.product.GiftCodeResponseDTO;
import me.caua.egiftstore.dto.product.GiftCompanyResponseDTO;
import me.caua.egiftstore.enums.GiftState;
import me.caua.egiftstore.service.GiftCardService;
import me.caua.egiftstore.service.GiftCodeService;
import me.caua.egiftstore.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GiftCodeResourceTest {

    @Inject
    GiftCodeService giftCodeService;
    @Inject
    GiftCardService giftCardService;
    @Inject
    TestUtils testUtils;
    @Test
    public void createTest() {
        GiftCardResponseDTO response = createFakeGiftCard("create");

        GiftCodeDTO giftCodeDTO =
                new GiftCodeDTO("codigo teste create", GiftState.AVAILABLE, response.id());

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(giftCodeDTO)
                .when()
                .post("/giftcode")
                .then()
                .statusCode(201)
                .body("giftCode", is(giftCodeDTO.giftCode()));
    }

    @Test
    public void updateTest() {
        GiftCodeResponseDTO response = createFakeGiftCode("testeUpdate");

        GiftCodeDTO giftCodeDTO2 =
                new GiftCodeDTO("codigo teste update", GiftState.CLAIMED, response.giftcardId());

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(giftCodeDTO2)
                .when()
                .pathParam("id", response.id())
                .put("/giftcode/{id}")
                .then()
                .statusCode(204);

        giftCardService.delete(response.giftcardId());
    }

    @Test
    public void findAllTest() {
        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .get("/giftcode")
                .then()
                .statusCode(200);
    }

    @Test
    public void findByIdTest() {
        GiftCodeResponseDTO response = createFakeGiftCode("testeFindId");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.id())
                .get("/giftcode/{id}")
                .then()
                .statusCode(200)
                .body("id", is(response.id().intValue()));

        giftCardService.delete(response.giftcardId());
    }

    @Test
    public void findByGiftCardTest() {
        GiftCodeResponseDTO response = createFakeGiftCode("testeFindByGift");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.giftcardId())
                .get("/giftcode/search/giftcard/{id}")
                .then()
                .statusCode(200)
                .body("giftCode", hasItem(response.giftCode()));

        giftCardService.delete(response.giftcardId());
    }

    @Test
    public void deleteTest() {
        GiftCodeResponseDTO response = createFakeGiftCode("testeDelete");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.id())
                .delete("/giftcode/{id}")
                .then()
                .statusCode(204);
    }

    public GiftCodeResponseDTO createFakeGiftCode(String uniqueId) {
        GiftCardResponseDTO response = createFakeGiftCard(uniqueId);

        GiftCodeDTO giftCodeDTO =
                new GiftCodeDTO("codigo teste giftcode " + uniqueId, GiftState.AVAILABLE, response.id());

        return giftCodeService.create(giftCodeDTO);
    }

    public GiftCardResponseDTO createFakeGiftCard(String uniqueId) {
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
