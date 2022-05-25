import details.Courier;
import details.CourierClient;
import details.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourierTest {

    private CourierClient courierClient;
    private int courierId;

   @Before
    public void setUp() {
       courierClient = new CourierClient();
    }

    @After
    public void teardown() {
       courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create new Courier")
    public void createNewCourier() {
        Courier courier = Courier.getCourierOne();
        boolean created = courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);
        assertTrue(created);
    }

    @Test
    @DisplayName("Create the same Courier")
    public void createTheSameCourier() {
        Courier courier = Courier.getCourierTwo();
        boolean created = courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);

        Courier courier2 = Courier.getCourierTwo();
        String result = courierClient.createNext(courier2);
        assertEquals( "Ответ метода отличается от ожидаемого результата", "Этот логин уже используется. Попробуйте другой.", result);
    }

    @Test
    @DisplayName("Create Courier with the same login")
    public void createCourierWithTheSameLogin() {
        Courier courier = Courier.getCourierTwo();
        boolean created = courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);

        Courier courier2 = Courier.getCourierThree();
        String result = courierClient.createNext(courier2);
        assertEquals("Ответ метода отличается от ожидаемого результата", "Этот логин уже используется. Попробуйте другой.", result);
    }
}
