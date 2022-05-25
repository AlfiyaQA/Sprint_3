package details;

public class CourierClient extends RestAssuredClient {

    private final String REGISTRATION = "/courier";
    private final String LOGIN = REGISTRATION + "/login";
    private final String COURIER = REGISTRATION + "/{courierId}";

    public boolean create(Courier courier) {
        return reqSpec
                .body(courier)
                .when()
                .post(REGISTRATION)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    public String createNext(Courier courier2) {
        return reqSpec
                .body(courier2)
                .when()
                .post(REGISTRATION)
                .then().log().all()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }

    public String createWithoutData(Courier courier) {
        return reqSpec
                .body(courier)
                .when()
                .post(REGISTRATION)
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    public int login (CourierCredentials creds) {
        return reqSpec
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public String loginIncorrectData(CourierCredentials credentials) {
        return reqSpec
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    public String loginWithoutData(CourierCredentials credentials) {
        return reqSpec
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    public void delete(int courierId) {
        reqSpec
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
