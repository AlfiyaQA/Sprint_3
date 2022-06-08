package request;

import data.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class OrderClient extends RestAssuredClient {

    private final String ORDER = "/orders";
    private final String LIST = "/orders";
    private final String TRACK = ORDER + "/cancel";

    @Step("Make new order")
    public ValidatableResponse makeOrder(Order order) {
        return reqSpec
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    @Step("Get list of all orders")
    public ValidatableResponse getOrders() {
        return reqSpec
                .get(LIST)
                .then().log().all();
    }

    @Step("Cancel order")
    public void cancelOrder(int track) {
        reqSpec
                .body(track)
                .when()
                .put(TRACK)
                .then().log().all()
                .assertThat()
                .statusCode(SC_OK);
    }
}
