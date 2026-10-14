package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginCourier {
    private String login;
    private String password;

    public static LoginCourier credsFromCourier(CreateCourier courier) {
        return new LoginCourier(courier.getLogin(), courier.getPassword());
    }
}
