package details;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

public class MakingOrder extends RestAssuredClient {

    private final String ORDER = "/orders";
    private final String LIST = "/orders";
    private final String TRACK = ORDER + "/cancel";

    public int makeOrder(Order order) {
        return reqSpec
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    public List<String> getOrders() {
        return reqSpec
                .get(LIST)
                .then().log().all()
                .assertThat()
                .body("orders.id", notNullValue())
                .statusCode(200)
                .extract()
                .path("orders");
    }

    public void cancel(int track) {
        reqSpec
                .body(track)
                .when()
                .put(TRACK)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
