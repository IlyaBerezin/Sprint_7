package org.example.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.client.CourierClient;
import org.example.generator.GenerateCourier;
import org.example.model.CourierCredsResponse;
import org.example.model.CreateCourier;
import org.example.model.LoginCourier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.example.model.LoginCourier.credsFromCourier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginCourierTest {

    private CreateCourier courier;
    private final CourierClient courierClient = new CourierClient();
    //private LoginCourier loginData;
    private String courierId;

    @Test
    @DisplayName("Логин курьера в системе по ручке со всеме полями /api/v1/courier/login") // имя теста
    @Description("При успешной авторизации: код 200 и возвращает id курьера для ручки /api/v1/courier/login")
        // описание теста
    void postLoginCourierStatusCode() {
        courier = GenerateCourier.generateValidCourier();
        Response response = courierClient.create(courier);
        Response loginResponse = courierClient.login(credsFromCourier(courier));
        //Логинимся и проверяем 200
        assertEquals(200, loginResponse.statusCode());
        // При успешной авторизации вернётся id курьера
        Object id = loginResponse.path("id");
        assertNotNull(id, "В ответе не пришёл id курьера");
        courierId = id.toString();

        //сохранить ID для tearDown
        courierId = loginResponse.as(CourierCredsResponse.class).getId();

    }

    @Test
    @DisplayName("Авторизация курьера без логина — ошибка 400")
    @Description("Передача пустого значения логина для ручки /api/v1/courier/login")
    void postLoginCourierWithoutLogin() {
        courier = GenerateCourier.generateValidCourier();
        Response createResponse = courierClient.create(courier);
        // логин передаём пустым, пароль оставляем
        LoginCourier creds = new LoginCourier("", courier.getPassword());
        // Пытаемся залогиниться
        Response loginResponse = courierClient.login(creds);

        // проверяем, что сервер не пустил: статус 400
        assertEquals(400, loginResponse.statusCode());
    }
    @Test
    @DisplayName("Авторизация курьера без пароля — ошибка 400")
    @Description("Передача пустого значения пароля для ручки /api/v1/courier/login")
    void postLoginCourierWithoutPassword() {
        courier = GenerateCourier.generateValidCourier();
        Response createResponse = courierClient.create(courier);
        // пароль передаём пустым, логин оставляем
        LoginCourier creds = new LoginCourier(courier.getLogin(), "");
        // Пытаемся залогиниться
        Response loginResponse = courierClient.login(creds);

        // проверяем, что сервер не пустил: статус 400
        assertEquals(400, loginResponse.statusCode());
    }
    @Test
    @DisplayName("Авторизация курьера с неверным паролем — ошибка 400")
    @Description("Передача некорректного пароля для ручки /api/v1/courier/login")
    void postLoginCourierWrongPassword() {
        courier = GenerateCourier.generateValidCourier();
        Response createResponse = courierClient.create(courier);
        // пароль передаём пустым, логин оставляем
        LoginCourier creds = new LoginCourier(courier.getLogin(), "gsgls94550");
        // Пытаемся залогиниться
        Response loginResponse = courierClient.login(creds);

        // проверяем, что сервер не пустил: статус 400
        assertEquals(404, loginResponse.statusCode());
    }
    @Test
    @DisplayName("Авторизация несуществующего курьера — ошибка 404")
    @Description("Пытаемся авторизоваться без созданного курьера для ручки /api/v1/courier/login")
    void postLoginCourierNonExistentUser() {
        courier = GenerateCourier.generateValidCourier();
        // НЕ создаём курьера на сервере — только генерируем данные

        Response loginResponse = courierClient.login(LoginCourier.credsFromCourier(courier));

        assertEquals(404, loginResponse.statusCode());
    }
    @AfterEach
    void tearDown() {
        courierClient.delete(courierId);
    }
}
