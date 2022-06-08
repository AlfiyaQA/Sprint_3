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

public class LoginTest {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void teardown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Login using correct data")
    public void loginUsingCorrectData() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_OK, statusCode);
        courierId = loginResponse.extract().path("id");
        assertNotEquals("Сравниваемые значения равны", 0, courierId);
    }

    @Test
    @DisplayName("Login using incorrect login")
    public void loginUsingIncorrectLogin() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        ValidatableResponse loginResponse2 = courierClient.loginCourier(new CourierCredentials(RandomStringUtils.randomAlphanumeric(20), courier.getPassword()));
        int statusCode = loginResponse2.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_NOT_FOUND, statusCode);
        String message = loginResponse2.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Login using incorrect password")
    public void loginUsingIncorrectPassword() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        ValidatableResponse loginResponse2  = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphanumeric(20)));
        int statusCode = loginResponse2.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_NOT_FOUND, statusCode);
        String message = loginResponse2.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Login with non-existing data")
    public void loginUsingNonExistingData() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        ValidatableResponse loginResponse2 = courierClient.loginCourier(new CourierCredentials(RandomStringUtils.randomAlphanumeric(20), RandomStringUtils.randomAlphanumeric(20)));
        int statusCode = loginResponse2.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_NOT_FOUND, statusCode);
        String message = loginResponse2.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Login without required fields")
    public void loginWithoutRequiredFields() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(creds);
        courierId = loginResponse.extract().path("id");

        ValidatableResponse loginResponse2 = courierClient.loginCourier(new CourierCredentials(null, courier.getPassword()));
        int statusCode = loginResponse2.extract().statusCode();
        assertEquals("Код статуса отличается от ожидаемого результата", SC_BAD_REQUEST, statusCode);
        String message = loginResponse2.extract().path("message");
        assertEquals("Ответ метода отличается от ожидаемого результата", "Недостаточно данных для входа", message);
    }
}

