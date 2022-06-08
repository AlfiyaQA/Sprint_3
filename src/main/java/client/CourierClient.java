package request;

import data.Courier;
import data.CourierCredentials;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.Data;

import static org.apache.http.HttpStatus.*;

public class CourierClient extends RestAssuredClient {

    private final String REGISTRATION = "/courier";
    private final String LOGIN = REGISTRATION + "/login";
    private final String COURIER = REGISTRATION + "/{courierId}";

    @Step("")
    public ValidatableResponse createCourier(Courier courier) {
        return reqSpec
                .body(courier)
                .when()
                .post(REGISTRATION)
                .then().log().all();
    }


    public ValidatableResponse loginCourier(CourierCredentials creds) {
        return reqSpec
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    //@Step("Delete courier")
    public void deleteCourier(int courierId) {
        reqSpec
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(SC_OK);
    }
}
