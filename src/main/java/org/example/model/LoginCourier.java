package org.example.model;

public class LoginCourier {

    private String login;
    private String password;


    public LoginCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static LoginCourier credsFromCourier(CreateCourier courier) {
        return new LoginCourier(courier.getLogin(), courier.getPassword());
    }
}
