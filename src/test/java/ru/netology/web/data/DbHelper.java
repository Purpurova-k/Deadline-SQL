package ru.netology.web.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {

    private DbHelper() {
    }

    private final static Connection conn = getConnection();
    private final static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
    }


    @SneakyThrows
    public static String getLogin(int id) {
        return runner.query(conn, "SELECT login FROM users WHERE id = " + id, new ScalarHandler<>());
    }


    @SneakyThrows
    public static String getPassword(int id) {
        return runner.query(conn, "SELECT password FROM users WHERE id = " + id, new ScalarHandler<>());
    }


    @SneakyThrows
    public static String getVerificationCode() {
        return runner.query(conn, "SELECT code FROM auth_codes;", new ScalarHandler<>());
    }


    @SneakyThrows
    public static void addUser(int id) {
        runner.update(conn, "INSERT INTO users(id, login, password) VALUES(?, ?, ?);",
                id, DataHelper.getRandomLogin(), DataHelper.getRandomPassword());
    }


    @SneakyThrows
    public static void cleanAllTables() {
        runner.execute(conn, "DELETE FROM users;");
        runner.execute(conn, "DELETE FROM cards;");
        runner.execute(conn, "DELETE FROM card_transactions;");
        runner.execute(conn, "DELETE FROM auth_codes;");
    }
}
