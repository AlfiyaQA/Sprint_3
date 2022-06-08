import model.Courier;
import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierWithoutRequiredFieldsTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Create courier without password")
    public void createCourierWithoutPassword() {
        Courier courier = Courier.getRandom();
        courier.setPassword(null);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_BAD_REQUEST, statusCode);
        String message = createResponse.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Create courier without login")
    public void createCourierWithoutLogin() {
        Courier courier = Courier.getRandom();
        courier.setLogin(null);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_BAD_REQUEST, statusCode);
        String message = createResponse.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Недостаточно данных для создания учетной записи", message);
    }
}
