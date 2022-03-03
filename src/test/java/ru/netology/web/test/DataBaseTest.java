package ru.netology.web.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.data.DbHelper.getLogin;
import static ru.netology.web.data.DbHelper.getVerificationCode;

public class DataBaseTest {

    String password = "$2a$10$JlkjRmwQFhPbckpr2MXqH.2ts2PJled0sfDrWhR5P2n7.BUyDu/76";

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }


    @AfterEach
    void cleanAllTables() {
        DbHelper.cleanAllTables();
    }


    @Test
    void shouldLogIn() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = new LoginPage().validLogin(authInfo);
        verificationPage.validVerify(getVerificationCode(authInfo));
    }


    @Test
    void shouldWarnIfLoginIncorrect() {
        LoginPage loginPage = new LoginPage();
        loginPage.inValidLogin(getOtherAuthInfo());
    }


    @Test
    void shouldWarnIfVerificationCodeIncorrect() {
        var verificationPage = new LoginPage().validLogin(getAuthInfo());
        verificationPage.inValidVerify(getRandomVerificationCode());
    }


    @Test
    void shouldBlockSystemAfterThreeTimesIncorrectPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.validLogin(getOtherAuthInfo());
        loginPage.clearFields();
        loginPage.inValidLogin(getOtherAuthInfo());
        loginPage.clearFields();
        loginPage.inValidLogin(getOtherAuthInfo());
        //TODO: где и что должно блокироваться, уведомление или пользователь блокируется?
    }


    @Test
    void shouldAddUserAndLogin() {
        DbHelper.addUser(100, password);
        var user = new AuthInfo(getLogin(100), password);
        var verificationPage = new LoginPage().validLogin(user);
        verificationPage.validVerify(getVerificationCode(user));
    }
}
