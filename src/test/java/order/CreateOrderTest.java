package order;

import io.restassured.response.Response;
import org.example.client.OrderClient;
import org.example.model.CreateOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {

    private final OrderClient orderClient = new OrderClient();

    // Источник данных: 4 сценария из задания
    static Stream<Arguments> colorScenarios() {
        return Stream.of(
                Arguments.of(List.of("BLACK")),              // один цвет BLACK
                Arguments.of(List.of("GREY")),               // один цвет GREY
                Arguments.of(List.of("BLACK", "GREY")),      // оба цвета
                Arguments.of(List.of())                       // без цвета (пустой список)
        );
    }

    @ParameterizedTest(name = "Создание заказа с цветом: {0}")
    @MethodSource("colorScenarios")
    void createOrderWithColorVariants(List<String> color) {
        // Создаём заказ с нужным цветом
        CreateOrder order = CreateOrder.generationDataOfOrder();
        order.setColor(color);

        Response response = orderClient.createNewOrder(order);

        // Заказ создаётся успешно
        assertEquals(201, response.statusCode());

        // В ответе есть track
        Object track = response.path("track");
        assertNotNull(track, "В ответе отсутствует поле track");
    }
}
