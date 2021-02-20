package org.jaspinall.file;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;


@QuarkusTest
public class FileResourceTest {
//    @Test
//    public void testRedisOperations() {
//        // verify that we have nothing
//        given()
//                .accept(ContentType.JSON)
//                .when()
//                .get("/increments")
//                .then()
//                .statusCode(200)
//                .body("size()", is(0));
//
//        // create a first increment key with an initial value of 0
//        given()
//                .contentType(ContentType.TEXT)
//                .accept(ContentType.TEXT)
//                .body("Hello Jordan!")
//                .when()
//                .post("/files")
//                .then()
//                .statusCode(200)
//                .body("key", is("first-key"))
//                .body("value", is(0));
//    }
}
