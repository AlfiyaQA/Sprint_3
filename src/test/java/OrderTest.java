import details.MakingOrder;
import details.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderTest {

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
        int track = makingOrder.makeOrder(order);
        assertNotEquals(0, track);
    }
}

