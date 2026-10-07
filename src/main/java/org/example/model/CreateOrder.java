package org.example.model;

import java.util.List;

public class CreateOrder {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    //Конструкторы со всеми параметрами и без
    public CreateOrder(String firstName, String lastName, String address, String metroStation,
                       String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
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
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setMetroStation(String metroStation) { this.metroStation = metroStation; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRentTime(int rentTime) { this.rentTime = rentTime; }
    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }
    public void setComment(String comment) { this.comment = comment; }
    public void setColor(List<String> color) { this.color = color; }

    public CreateOrder() {
    }

    public static CreateOrder generationDataOfOrder() {
        CreateOrder order = new CreateOrder();
        order.setFirstName("Березин");
        order.setLastName("Илья");
        order.setAddress("ул. Шпалерная, 60");
        order.setMetroStation("9");
        order.setPhone("+79990000000");
        order.setRentTime(5);
        order.setDeliveryDate("2026-11-10");
        order.setComment("Позвонить за 2 часа");
        order.setColor(null);
        return order;
    }
}
