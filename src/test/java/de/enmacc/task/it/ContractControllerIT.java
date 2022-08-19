package de.enmacc.task.it;

import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.exception.ErrorResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContractControllerIT {

    @LocalServerPort
    protected int port;
    private String json;

    @BeforeEach
    public void init() {
        //language=JSON
        json = """
                {
                  "companyA": "A",
                  "companyB": "B"
                }""";
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DirtiesContext
    @Order(1)
    public void testAddContract() {
        Response response = given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts");

        response.then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        ContractDto contractDto = response.getBody().as(ContractDto.class);
        assertEquals("A", contractDto.getCompanyA());
        assertEquals("B", contractDto.getCompanyB());
    }

    @Test
    @Order(2)
    public void testGenerateMockData() {
        Response response = given()
                .when()
                .post("generateMockData");


        response.then()
                .statusCode(HttpStatus.SC_OK);
        List responseList = response.getBody().as(List.class);
        assertEquals(6, responseList.size());
    }

    @Test
    @Order(3)
    public void testGetAllPossibleSleeves() {
        Response response = given()
            .contentType(ContentType.JSON)
            .param("companyA", "A")
            .param("companyB", "B")
        .when()
            .get("sleeves");

        response.then()
            .statusCode(HttpStatus.SC_OK);
        JsonPath jsonPath = response.getBody().jsonPath();
        List<String> list = jsonPath.getList("$");
        Assertions.assertTrue(list.containsAll(List.of("A->B", "A->C->B", "A->E->B")));
    }

    @Test
    @Order(4)
    public void testAddContractFailedValidations() {
        //language=JSON
        String invalidJson = """
                {
                  "companyB": ""
                }""";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .post("contracts");

        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        ErrorResponse errorResponse = response.getBody().as(ErrorResponse.class);
        assertEquals(2, errorResponse.getErrors().size());
        assertTrue(errorResponse.getErrors().contains("Company name cannot be blank"));
    }

    @Test
    @DirtiesContext
    @Order(5)
    public void testAddContractAlreadyExistsException() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("contracts");

        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;

        ErrorResponse errorResponse = response.getBody().as(ErrorResponse.class);
        assertEquals(1, errorResponse.getErrors().size());
        assertTrue(errorResponse.getErrors().contains("A contract between companies A and B already exists"));
    }
}