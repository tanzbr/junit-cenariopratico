package me.caua.egiftstore.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import me.caua.egiftstore.dto.product.GiftCardDTO;
import me.caua.egiftstore.dto.product.GiftCodeDTO;
import me.caua.egiftstore.dto.product.GiftCardResponseDTO;
import me.caua.egiftstore.dto.product.GiftCompanyResponseDTO;
import me.caua.egiftstore.enums.GiftState;
import me.caua.egiftstore.form.ImageForm;
import me.caua.egiftstore.service.GiftCardService;
import me.caua.egiftstore.service.GiftCompanyService;
import me.caua.egiftstore.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GiftCardResourceTest {

    @Inject
    GiftCardService giftCardService;
    @Inject
    GiftCompanyService giftCompanyService;
    @Inject
    TestUtils testUtils;

    @Test
    public void createTest() {
        GiftCompanyResponseDTO giftCompanyResponseDTO = testUtils.createFakeCompany();

        GiftCodeDTO giftCodeDTO =
                new GiftCodeDTO("codigoteste1", GiftState.AVAILABLE, null);
        GiftCodeDTO giftCodeDTO2 =
                new GiftCodeDTO("codigoteste2", GiftState.AVAILABLE, null);


        GiftCardDTO giftCardDTO =
                new GiftCardDTO(
                        "Gift Card Google",
                        "Gift Card para a Play Store",
                        40.0,
                        giftCompanyResponseDTO.id(),
                        List.of("tag1", "tag2"),
                        List.of(giftCodeDTO, giftCodeDTO2),
                        true
                        );

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(giftCardDTO)
                .when()
                .post("/giftcard")
                .then()
                .statusCode(201)
                .body("name", is(giftCardDTO.name()));
    }

    @Test
    public void updateTest() {
        GiftCardResponseDTO response = createFakeGiftCard("update");

        GiftCodeDTO giftCodeDTO =
                new GiftCodeDTO("codigoteste4", GiftState.AVAILABLE, null);
        GiftCodeDTO giftCodeDTO2 =
                new GiftCodeDTO("codigoteste5", GiftState.AVAILABLE, null);


        GiftCardDTO giftCardDTO =
                new GiftCardDTO(
                        "Gift Card Google Editado",
                        "Gift Card para a Play Store Editado",
                        400.0,
                        response.company().id(),
                        List.of("tag1", "tag2"),
                        List.of(giftCodeDTO, giftCodeDTO2),
                        true
                );

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .contentType(MediaType.APPLICATION_JSON)
                .body(giftCardDTO)
                .when()
                .pathParam("id", response.id())
                .put("/giftcard/{id}")
                .then()
                .statusCode(204);

        giftCardService.delete(response.id());
    }

    private GiftCardDTO getGiftCardDTO() {
        GiftCodeDTO giftCodeDTO =
                new GiftCodeDTO("codigoteste4", GiftState.AVAILABLE, null);
        GiftCodeDTO giftCodeDTO2 =
                new GiftCodeDTO("codigoteste5", GiftState.AVAILABLE, null);


        GiftCardDTO giftCardDTO =
                new GiftCardDTO(
                        "Gift Card Google Editado2",
                        "Gift Card para a Play Store Editado2",
                        40.0,
                        1L,
                        List.of("tag1", "tag2"),
                        List.of(giftCodeDTO, giftCodeDTO2),
                        false
                );
        return giftCardDTO;
    }

    @Test
    public void findAllTest() {
        given()
            .when()
            .get("/giftcard")
            .then()
            .statusCode(200);
    }

    @Test
    public void findByIdTest() {
        GiftCardResponseDTO response = createFakeGiftCard("findId");

        given()
                .when()
                .pathParam("id", response.id())
                .get("/giftcard/{id}")
                .then()
                .statusCode(200)
                .body("id", is(response.id().intValue()));

        giftCardService.delete(response.id());
    }

    @Test
    public void findByNameTest() {
        GiftCardResponseDTO response = createFakeGiftCard("findName");

        given()
                .when()
                .pathParam("name", response.name())
                .get("/giftcard/search/name/{name}")
                .then()
                .statusCode(200)
                .body("name", hasItem(response.name()));

        giftCardService.delete(response.id());
    }

    @Test
    public void deleteTest() {
        GiftCardResponseDTO response = createFakeGiftCard("delete");

        given()
                .header("Authorization", "Bearer " + testUtils.getAuth())
                .when()
                .pathParam("id", response.id())
                .delete("/giftcard/{id}")
                .then()
                .statusCode(204);
    }

//    @Test
//    public void saveImageTest() {
//        GiftCardResponseDTO response = createFakeGiftCard("testImage");
//        ImageForm imageForm = new ImageForm();
//        imageForm.setImageName("testeImage.png");
//
//        byte[] decodedBytes = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAMAAACdt4HsAAAAWlBMVEUAAAAAAAAYEhIgGBwjHigoGxwrJzc0L0I1ICI1MTo2ICJHIC5VOj9bKzpnQUFwPDx+T0eRbVyigGmmi22vVkywk3S0nnS9roLEu4nWakbZdlXeupHqh0bw3qlT+TO1AAAAAXRSTlMAQObYZgAAAuJJREFUWMO9louWoyAMhi2gjrOO7ThY62jf/zU3gYSbaHUv858eBS2fgYSQoiBNi9GkSUWiW3TL6McAqlBGpwEd3ZTaACykmbRl6V8DNuUAT6MfBHRd29ICfn/TQrZt1/Gi3m63bjUGHjq3dvNzZgKPh0ce4D0QOsQDFvz7BFpgCFxAiHS4osvaHfhft7Oe9DtoWfAKnbnVDsh/hjd0SUyarLR+w++9aT3FermImgETfnHSvg8tuLiPYKxOxRoK/zRum5/4Cu8gF9Ac0vCsKMp3aiQArU3okf9RWqeAF8LYHsfxzn1JSuNeSQnIy+UAQIDOAOTQRwCBsxFSyR4k/zMATSwB0PdKNdA2kydAKDMX17s4xYDGzrmU1XOuZKkSyVcA5QD1XNUMkOa3b4EgAKkUdVXXggDC/GxTWPeEADtnBHgDGWDfIUDIAAC9ANAbDT1rbUEwhRxgsCLOMNAaqLpeL6IDiDXg08gBKlU5LzQNe+cE4ON6/YgBtB5rAE3dAngN5C+Qm0LjAyzjhc9EawssAPMVb/FlCQCj1d0IGhZg9kAEsBnKCBJPAGDB2CLNB6dOpsfjEQD89ZhkYkF0OwiILPgTAKrh+WPyfZrmwfWQqz4czwcNsPvF1x4EmItTAMHOI4BwlwPTNyFqBpgwPQ3IahuAH4mf0G50Hb+o8p8BmiYcnhIoIdlO7wDXawIwzqLtHCFsOvC9LQuCaDqwzWIL9jU+xofbB9yQJ7dCmhBwvvegD/F1oNQItmMGME36RELoB3nWgjghZAAHLCCXKYUnlE+ocXWQqU7ShBADmiaoL/IAu4FlcABGAPXSAhtVwlUQFiDK+DzdAdi4pnrInFsG4GqFEIBn4st4SsqFPgCYQ3W9n4M9jbuZ6wSuG3YBIgPgY56P/RzAIYSPHP7gBsAuBy4i9QmYZAXMBlwncN0QAAIv7Jw1ab3AFYEtD7a8gBnBJoaC64QvENYNVBFgeZAF/Aav/WNq8Vg49AAAAABJRU5ErkJggg==");
//        imageForm.setImage(decodedBytes);
//
//        given()
//                .header("Authorization", "Bearer " + testUtils.getAuth())
//                .when()
//                .body(imageForm)
//                .pathParam("id", response.id())
//                .patch("/giftcard/{id}/image/upload")
//                .then()
//                .statusCode(204);
//    }

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
