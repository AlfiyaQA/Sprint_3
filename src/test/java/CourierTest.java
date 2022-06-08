import model.Courier;

import client.CourierClient;
import model.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class CourierTest {

    private CourierClient courierClient;
    public int courierId;

    @Before
    public void setUp() {
       courierClient = new CourierClient();
    }

    @After
    public void teardown() {
       courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Create new courier")
    public void createNewCourier() {
        Courier courier = Courier.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_CREATED, statusCode);
        boolean result = createResponse.extract().path("ok");
        assertTrue(result);
    }

    @Test
    @DisplayName("Create the same courier")
    public void createTheSameCourier() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int statusCode = createResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_CONFLICT, statusCode);
        String message = createResponse.extract().path("message");
        assertEquals( "Ответ метода отличается от ожидаемого результата", "Этот логин уже используется. Попробуйте другой.", message);
    }

    @Test
    @DisplayName("Create courier with the same login")
    public void createCourierWithTheSameLogin() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        courier.setPassword(RandomStringUtils.randomAlphanumeric(20));
        courier.setFirstName(RandomStringUtils.randomAlphanumeric(20));

        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int statusCode = createResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_CONFLICT, statusCode);
        String message = createResponse.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Этот логин уже используется. Попробуйте другой.", message);
    }
}
