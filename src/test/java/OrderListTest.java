import details.MakingOrder;
import details.Order;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNotEquals;

public class OrderListTest {

    private MakingOrder makingOrder;
    private int track;

    @Before
    public void setUp() {
        makingOrder = new MakingOrder();
    }

    @After
    public void teardown() {
        makingOrder.cancel(track);
    }

    @Test
    @DisplayName("Get order list")
    public void getOrderList() {
        Order order = Order.getOrder();
        int track = makingOrder.makeOrder(order);
        List<String> orders = makingOrder.getOrders();
        assertNotEquals(0, orders);
    }
}

