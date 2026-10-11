package org.example.courier;

import org.example.generator.GenerateCourier;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.client.CourierClient;
import org.example.model.CreateCourier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        courier = GenerateCourier.generateValidCourier();
        Response firstResponse = courierClient.create(courier);
        assertEquals(201, firstResponse.statusCode());
        assertEquals(true, firstResponse.path("ok"));
        // Сохраняем ID для удаления в @AfterEach
        Object idObj = firstResponse.path("id");       // Может быть null
        if (idObj != null) {
            id = idObj.toString();
        }
    }

    @Test
    @DisplayName("Создание курьера с пустым паролем")
    @Description("Проверка ошибки при пустом пароле для ручки /api/v1/courier")
    void postCreateCourierWithEmptyPassword() {
        courier = GenerateCourier.generateValidCourier();
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
        courier = GenerateCourier.generateValidCourier();
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
        courier = GenerateCourier.generateValidCourier();
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
        courier = GenerateCourier.generateValidCourier();
        Response firstResponse = courierClient.create(courier);
        // 2. Пытаемся создать курьера с теми же данными ещё раз
        Response secondResponse = courierClient.create(courier);
        // 3. Проверяем, что сервер вернул 409 (Conflict)
        assertEquals(409, secondResponse.statusCode());
        Object idObj = firstResponse.path("id");
        if (idObj != null) {
            id = idObj.toString();
        }
    }

    @AfterEach
    public void tearDown() {
        if (id != null) {
            try {
                courierClient.delete(id);
                System.out.println("Курьер с id=" + id + " удалён");
            } catch (Exception e) {
                System.out.println("Не удалось удалить курьера с id=" + id + ": " + e.getMessage());
            } finally {
                id = null; // сбрасываем, чтобы не удалить лишнего в следующем тесте
            }
        } else {
            System.out.println("ID не найден — удаление не требуется");
        }
    }
}