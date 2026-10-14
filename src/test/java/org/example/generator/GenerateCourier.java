package org.example.generator;

import com.github.javafaker.Faker;
import org.example.model.CreateCourier;

public class GenerateCourier {
    private static final Faker faker = new Faker();

    public static CreateCourier generateValidCourier() {
        return new CreateCourier(
        faker.internet().emailAddress().split("@")[0],
        faker.internet().password(8, 16),
        faker.name().firstName()
        );
    }
}

