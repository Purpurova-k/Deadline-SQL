package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.data.DbHelper.getLogin;
import static ru.netology.web.data.DbHelper.getVerificationCode;

public class DataBaseTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }


    @AfterAll
    static void cleanAllTables() {
        DbHelper.cleanAllTables();
    }


    @Test
    void shouldLogIn() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = new LoginPage().validLogin(authInfo);
        verificationPage.validVerify(getVerificationCode(authInfo));
    }


    @Test
    void shouldWarnIfPasswordIncorrect() {
        LoginPage loginPage = new LoginPage();
        loginPage.inValidLogin(getOtherAuthInfo());
    }


    @Test
    void shouldWarnIfVerificationCodeIncorrect() {
        var verificationPage = new LoginPage().validLogin(getAuthInfo());
        verificationPage.inValidVerify(getRandomVerificationCode());
    }


    // Падающий тест
    @Test
    void shouldBlockSystemAfterThreeTimesIncorrectPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.inValidLogin(getOtherAuthInfo());
        loginPage.clearFields();
        loginPage.inValidLogin(getOtherAuthInfo());
        loginPage.clearFields();
        loginPage.inValidLogin(getOtherAuthInfo());

        String login = DataHelper.getOtherAuthInfo().getLogin();
        String userStatus = DbHelper.getUserStatus(login);

        loginPage.assertErrorNotificationVisible();
        assertEquals("blocked", userStatus);
    }


    @Test
    void shouldAddUserAndLogin() {
        var encryptedPassword = DataHelper.getPassword().getEncryptedPassword();
        var password = DataHelper.getPassword().getPassword();
        DbHelper.addUser(100, getRandomLogin(), encryptedPassword);
        var user = new AuthInfo(getLogin(100), password);
        var verificationPage = new LoginPage().validLogin(user);
        verificationPage.validVerify(getVerificationCode(user));
    }
}
