package order;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.client.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfOrdersTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка ответа, что возвращается список заказов")
    public void getOrderGetList() {
        Response response = orderClient.getListOrders();
        // Проверка, Status Code = 200 и поле "orders" не пустое
        assertEquals(200, response.statusCode(), "Статус должен быть 200");
        // Поле "orders" присутствует и является списком
        List<?> orders = response.path("orders");
        assertNotNull(orders, "Поле 'orders' должно присутствовать в ответе");
        assertTrue(orders instanceof List, "Поле 'orders' должно быть списком");
    }
}
