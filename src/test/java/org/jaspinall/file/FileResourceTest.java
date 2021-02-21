package org.jaspinall.file;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;


@QuarkusTest
public class FileResourceTest {
    @Test
    public void testRedisOperations() {
        // verify that we have nothing
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/files")
                .then()
                .statusCode(200)
                .body("size()", is(0));

        // create a first text
        given()
                .contentType(ContentType.TEXT)
                .accept(ContentType.TEXT)
                .body("Hello Jordan!")
                .when()
                .post("/files")
                .then()
                .statusCode(200)
                .body("key", is("first-key"))
                .body("value", is(0));
    }
}
