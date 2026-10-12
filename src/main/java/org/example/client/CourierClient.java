package org.example.client;

import org.example.model.CreateCourier;
import org.example.model.LoginCourier;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CourierClient {
    private static final String API_V1_COURIER = "/api/v1/courier";
    private static final String API_V1_COURIER_LOGIN = "/api/v1/courier/login";

    public CourierClient() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    public Response create(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER);
    }

    public Response login(LoginCourier creds) {
        return given()
                .contentType(JSON)
                .and()
                .body(creds)
                .when()
                .post(API_V1_COURIER_LOGIN);
    }

    public Response delete(String id) {
        return given()
                .contentType(JSON)
                .and()
                .when()
                .delete(String.format("%s/%s", API_V1_COURIER, id));
    }
}
