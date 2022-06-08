import client.OrderClient;
import model.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderTest {

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

    private List<String> color;
    public OrderTest (List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getList() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of("")}
        };
    }

    @Test
    @DisplayName("Make order using color")
    @Description("Variants of making order according to scooter's color: black, grey, both, without color")
    public void makeOrderUsingColor() {
        Order order = Order.getOrder();
        order.setColor(color);
        ValidatableResponse orderResponse = orderClient.makeOrder(order);

        int statusCode = orderResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_CREATED, statusCode);
        track = orderResponse.extract().path("track");
        assertNotEquals("Сравниваемые значения равны", 0, track);
    }
}

