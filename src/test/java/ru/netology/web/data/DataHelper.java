package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;


public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        var faker = new Faker();
        return new AuthInfo("vasya", faker.internet().password());
    }

    public static String getRandomLogin() {
        var faker = new Faker();
        return faker.name().username();
    }

    public static String getRandomVerificationCode() {
        var faker = new Faker();
        return faker.number().digits(5);
    }
}
