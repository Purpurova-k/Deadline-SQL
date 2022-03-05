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
        return faker.name().firstName().toLowerCase();
    }

    public static String getRandomVerificationCode() {
        var faker = new Faker();
        return faker.number().digits(5);
    }


    @Value
    public static class PasswordInfo {
        private String password;
        private String encryptedPassword;
    }

    public static PasswordInfo getPassword() {
        return new PasswordInfo("qwerty123", "$2a$10$H4Gl9LRlQdHQT62fsio9c.OErcZL2yAAoo8K5wQfcJ.Ay/0jkPGZ.");
    }

}
