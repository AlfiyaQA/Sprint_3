import details.Courier;
import details.CourierClient;
import details.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

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
    @DisplayName("Login using correct data")
    public void loginUsingCorrectData() {
        Courier courier = Courier.getCourierOne();
        courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Login using incorrect login")
    public void loginUsingIncorrectLogin() {
        Courier courier = Courier.getCourierOne();
        courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);

        CourierCredentials credentials = new CourierCredentials(RandomStringUtils.randomAlphanumeric(20), courier.getPassword());
        String result = courierClient.loginIncorrectData(credentials);
        assertEquals("Ответ метода отличается от ожидаемого результата", "Учетная запись не найдена", result);
    }

    @Test
    @DisplayName("Login using incorrect password")
    public void loginUsingIncorrectPassword() {
        Courier courier = Courier.getCourierOne();
        courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);

        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphanumeric(20));
        String result = courierClient.loginIncorrectData(credentials);
        assertEquals("Ответ метода отличается от ожидаемого результата", "Учетная запись не найдена", result);
    }

    @Test
    @DisplayName("Login with non-existing data")
    public void loginUsingNonExistingData() {
        Courier courier = Courier.getCourierOne();
        courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);

        CourierCredentials credentials = new CourierCredentials(RandomStringUtils.randomAlphanumeric(20), RandomStringUtils.randomAlphanumeric(20));
        String result = courierClient.loginIncorrectData(credentials);
        assertEquals("Ответ метода отличается от ожидаемого результата", "Учетная запись не найдена", result);
    }

    @Test
    @DisplayName("Login without required fields")
    public void loginWithoutRequiredFields() {
        Courier courier = Courier.getCourierOne();
        courierClient.create(courier);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds);

        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), "");
        String result = courierClient.loginWithoutData(credentials);
        assertEquals("Ответ метода отличается от ожидаемого результата", "Недостаточно данных для входа", result);
    }
}
