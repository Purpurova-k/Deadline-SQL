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

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static String getRandomLogin() {
        var faker = new Faker();
        return faker.name().username();
    }

    public static String getRandomPassword() {
        var faker = new Faker();
        return faker.internet().password();
    }

    public static String getRandomVerificationCode() {
        var faker = new Faker();
        return faker.number().digits(5);
    }
}
