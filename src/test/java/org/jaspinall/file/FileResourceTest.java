package org.jaspinall.file;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

import java.util.UUID;


@QuarkusTest
class FileResourceTest {

    @BeforeEach
    void initEach() {
        // verify that we have nothing in the datastore
        verifyEmpty();
    }

    void verifyEmpty() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/files")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    String uploadText(String text) {
        String key = UUID.randomUUID().toString();
        given()
                .contentType(ContentType.TEXT)
                .accept(ContentType.TEXT)
                .body(text)
                .when()
                .post("/files/" + key)
                .then()
                .statusCode(200)
                .body(equalTo(key));
        return key;
    }

    String getTextDetails(String key) {
        return given()
                .accept(ContentType.JSON)
                .when()
                .get("/files/" + key)
                .then()
                .statusCode(200)
                .extract().response().getBody().print();
    }

    void deleteText(String key) {
        given()
                .contentType(ContentType.TEXT)
                .when()
                .delete("/files/" + key)
                .then()
                .statusCode(204);
    }

    @Test
    void testExampleText() {
        // create example text
        String key = uploadText("Hello world & good morning. The date is 18/05/2016");

        // Check result from text
        String result = getTextDetails(key);
        assertThat(result, is("Word count = 9" + System.lineSeparator() +
                "Average word length = 4.556" + System.lineSeparator() +
                "Number of words of length 1 is 1" + System.lineSeparator() +
                "Number of words of length 2 is 1" + System.lineSeparator() +
                "Number of words of length 3 is 1" + System.lineSeparator() +
                "Number of words of length 4 is 2" + System.lineSeparator() +
                "Number of words of length 5 is 2" + System.lineSeparator() +
                "Number of words of length 7 is 1" + System.lineSeparator() +
                "Number of words of length 10 is 1" + System.lineSeparator() +
                "The most frequently occurring word length is 2, for word lengths of 4 & 5" + System.lineSeparator()));

        // verify deletion of example text
        deleteText(key);
    }

    @Test
    void testMultipleMaxWordLength() {
        String key = uploadText("Three different lengths");

        String result = getTextDetails(key);
        assertThat(result, is("Word count = 3" + System.lineSeparator() +
                "Average word length = 7" + System.lineSeparator() +
                "Number of words of length 5 is 1" + System.lineSeparator() +
                "Number of words of length 7 is 1" + System.lineSeparator() +
                "Number of words of length 9 is 1" + System.lineSeparator() +
                "The most frequently occurring word length is 1, for word lengths of 5, 7 & 9" + System.lineSeparator()));

        deleteText(key);
    }

    @Test
    void testIgnorePunctuation() {
        String key = uploadText("A sentence with ** lots of :random; punctuation!");

        String result = getTextDetails(key);
        assertThat(result, is("Word count = 7" + System.lineSeparator() +
                "Average word length = 5.143" + System.lineSeparator() +
                "Number of words of length 1 is 1" + System.lineSeparator() +
                "Number of words of length 2 is 1" + System.lineSeparator() +
                "Number of words of length 4 is 2" + System.lineSeparator() +
                "Number of words of length 6 is 1" + System.lineSeparator() +
                "Number of words of length 8 is 1" + System.lineSeparator() +
                "Number of words of length 11 is 1" + System.lineSeparator() +
                "The most frequently occurring word length is 2, for word lengths of 4" + System.lineSeparator()));

        deleteText(key);
    }
}
