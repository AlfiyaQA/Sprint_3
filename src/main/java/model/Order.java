package data;

import lombok.Data;
import java.util.List;

@Data
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getOrder() {
        return new Order(
                "Иван",
                "Иванов",
                "ул.Ленина, д.1, кв.2",
                "Сокольники",
                "+79870000000000",
                3,
                "2022-06-01",
                "только в будни",
        List.of("BLACK", "GREY"));
    }
}

