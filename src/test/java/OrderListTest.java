import io.restassured.response.ValidatableResponse;
import client.OrderClient;
import model.Order;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderListTest {

    private OrderClient orderClient;
    public int track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void teardown() {
        orderClient.cancelOrder(track);
    }

    @Test
    @DisplayName("Get order list")
    public void getOrderList() {
        Order order = Order.getOrder();
        ValidatableResponse orderResponse = orderClient.makeOrder(order);
        track = orderResponse.extract().path("track");

        ValidatableResponse orderListResponse = orderClient.getOrders();
        int statusCode = orderListResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_OK, statusCode);

        List<String> orders = orderListResponse.extract().path("orders");
        assertNotEquals("Сравниваемые значения равны", 0, orders.size());
        orderListResponse.assertThat().body("orders.id", notNullValue());
    }
}

