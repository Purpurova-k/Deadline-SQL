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
    public static String getVerificationCode(DataHelper.AuthInfo authInfo) {
        String login = authInfo.getLogin();
        var codeSQL = "SELECT code FROM auth_codes JOIN users ON auth_codes.user_id = users.id WHERE users.login = ?;";
        return runner.query(conn, codeSQL, new ScalarHandler<>(), login);
    }


    @SneakyThrows
    public static void addUser(int id, String password) {
        runner.update(conn, "INSERT INTO users(id, login, password) VALUES(?, ?, ?);",
                id, DataHelper.getRandomLogin(), password);
    }


    @SneakyThrows
    public static void cleanAllTables() {
        runner.execute(conn, "DELETE FROM app.auth_codes;");
        runner.execute(conn, "DELETE FROM app.cards;");
        runner.execute(conn, "DELETE FROM app.card_transactions;");
        runner.execute(conn, "DELETE FROM app.users;");
    }


    @SneakyThrows
    public static String getUserStatus(String login) {
        var codeSQL = "SElECT status FROM users WHERE login = ?;";
        return runner.query(conn, codeSQL, new ScalarHandler<>(), login);
    }
}
