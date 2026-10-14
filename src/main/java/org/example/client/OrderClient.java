package org.example.client;

import org.example.model.CreateOrder;
import io.restassured.RestAssured;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient {

    public final String POST_ORDER_CREATE = "/api/v1/orders";//API Создание заказа
    private final String GET_LIST_ORDER = "/api/v1/orders"; //Получение списка заказов

    public OrderClient() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    //Создание заказа
    public Response createNewOrder(CreateOrder order) {
        return given()
                .contentType(JSON)
                .body(order)
                .when()
                .post(POST_ORDER_CREATE);
    }

    //Получение списка заказов
    public Response getListOrders() {
        return given()
                .contentType(JSON)
                .when()
                .get(GET_LIST_ORDER);
    }
}
