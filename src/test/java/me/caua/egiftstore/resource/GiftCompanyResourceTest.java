package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.product.GiftCompanyDTO;
import me.caua.egiftstore.dto.product.GiftCompanyResponseDTO;
import me.caua.egiftstore.service.GiftCompanyService;
import me.caua.egiftstore.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GiftCompanyResourceTest {

    @Inject
    GiftCompanyService giftCompanyService;
    @Inject
    TestUtils testUtils;

    @Test
    public void createTest() {

        GiftCompanyDTO giftCompanyDTO =
                new GiftCompanyDTO(
                        "Google",
                        "11.111.111-0001/10"
                        );

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(giftCompanyDTO)
                .when()
                .post("/giftcompany")
                .then()
                .statusCode(201)
                .body("name", is(giftCompanyDTO.name()));
    }

    @Test
    public void updateTest() {
        GiftCompanyResponseDTO response = testUtils.createFakeCompany();
        GiftCompanyDTO giftCompanyDTO =
                new GiftCompanyDTO(
                        "Google Update",
                        "22.111.111-0001/10"
                        );

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(giftCompanyDTO)
                .when()
                .pathParam("id", response.id())
                .put("/giftcompany/{id}")
                .then()
                .statusCode(204);

        giftCompanyService.delete(response.id());
    }

    @Test
    public void findAllTest() {
        given()
            .when()
            .get("/giftcompany")
            .then()
            .statusCode(200);
    }

    @Test
    public void findByIdTest() {
        GiftCompanyResponseDTO response = testUtils.createFakeCompany();

        given()
                .when()
                .pathParam("id", response.id())
                .get("/giftcompany/{id}")
                .then()
                .statusCode(200)
                .body("id", is(response.id().intValue()));

        giftCompanyService.delete(response.id());
    }

    @Test
    public void findByNameTest() {
        GiftCompanyResponseDTO response = testUtils.createFakeCompany();

        given()
                .when()
                .pathParam("name", response.name())
                .get("/giftcompany/search/name/{name}")
                .then()
                .statusCode(200)
                .body("name", hasItem(response.name()));

        giftCompanyService.delete(response.id());
    }

    @Test
    public void deleteTest() {
        GiftCompanyResponseDTO response = testUtils.createFakeCompany();

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.id())
                .delete("/giftcompany/{id}")
                .then()
                .statusCode(204);
    }
}
