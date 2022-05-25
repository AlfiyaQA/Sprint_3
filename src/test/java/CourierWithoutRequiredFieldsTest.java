import details.Courier;
import details.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CourierWithoutRequiredFieldsTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Create Courier without required fields")
    public void createCourierWithoutRequiredFields() {
        Courier courier = Courier.getCourierFour();
        String result = courierClient.createWithoutData(courier);
        assertEquals("Ответ метода отличается от ожидаемого результата", "Недостаточно данных для создания учетной записи", result);
    }
}
