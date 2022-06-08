package data;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphanumeric(20);
        String password = RandomStringUtils.randomAlphanumeric(20);
        String lastName = RandomStringUtils.randomAlphanumeric(20);

        return new Courier(login, password, lastName);
    }

    /*public static Courier getCourierTwo() {
        String login = "portyma";
        String password = "12345";
        String lastName = "Иванов";

        return new Courier(login, password, lastName);
    }

    public static Courier getCourierThree() {
        String login = "portyma";
        String password = "6789";
        String lastName = "Петров";

        return new Courier(login, password, lastName);
    }

    public static Courier getCourierFour() {
        String login = RandomStringUtils.randomAlphanumeric(20);

        return new Courier(login, null, null);
    }*/


    public Courier getCourier() {
        return new Courier(login, password, firstName);
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
