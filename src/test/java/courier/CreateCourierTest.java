package courier;

import io.restassured.RestAssured;

import io.restassured.response.ValidatableResponse;
import org.example.client.CourierClient;
import org.example.model.CreateCourier;
import org.example.model.CourierCredsResponse;
import io.qameta.allure.Description; // импорт Description
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.example.model.CreateCourier.generationDataOfCourier;
import static org.example.model.LoginCourier.credsFromCourier;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCourierTest {

    private CreateCourier courier;
    private final CourierClient courierClient = new CourierClient();
    private String id;

    @Test
    @DisplayName("Проверка кода ответа и ok: true для ручки /api/v1/courier") // имя теста
    @Description("Переданы все поля и переданы валидными данными для ручки /api/v1/courier")
        // описание теста
    void postCreateCourierStatusCode() {
        courier = CreateCourier.generationDataOfCourier();
        Response response = courierClient.create(courier);
        assertEquals(201, response.statusCode());
        assertEquals(true, response.path("ok"));
    }

    @Test
    @DisplayName("Создание курьера с пустым паролем")
    @Description("Проверка ошибки при пустом пароле для ручки /api/v1/courier")
    void postCreateCourierWithEmptyPassword() {
        courier = CreateCourier.generationDataOfCourier();
        // Установка пустого пароля для курьера
        courier.setPassword("");
        // Создание курьера с параметрами
        Response response = courierClient.create(courier);
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Создание курьера с пустым логином")
    @Description("Проверка ошибки при пустом логине для ручки /api/v1/courier")
    void postCreateCourierWithEmptyLogin() {
        courier = CreateCourier.generationDataOfCourier();
        // Установка пустого пароля для курьера
        courier.setLogin("");
        // Создание курьера с параметрами
        Response response = courierClient.create(courier);
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Создание курьера с пустым именем")
    @Description("Проверка ошибки при пустом логине для ручки /api/v1/courier")
    void postCreateCourierWithEmptyFirstName() {
        courier = CreateCourier.generationDataOfCourier();
        // Установка пустого пароля для курьера
        courier.setFirstName("");
        // Создание курьера с параметрами
        Response response = courierClient.create(courier);
        assertEquals(400, response.statusCode());
    }
    @Test
    @DisplayName("Создание курьера с уже существующим логином — ошибка 409")
    @Description("Проверка, что при попытке создать курьера с логином, который уже есть в системе, возвращается 409 Conflict")
    void postCreateCourierWithDuplicateLogin() {
        // 1. Генерируем и создаём курьера первый раз
        courier = CreateCourier.generationDataOfCourier();
        courierClient.create(courier);
        // 2. Пытаемся создать курьера с теми же данными ещё раз
        Response response = courierClient.create(courier);
        // 3. Проверяем, что сервер вернул 409 (Conflict)
        assertEquals(409, response.statusCode());
    }

    @AfterEach
    public void tearDown() {
        courierClient.delete(id);
    }
}