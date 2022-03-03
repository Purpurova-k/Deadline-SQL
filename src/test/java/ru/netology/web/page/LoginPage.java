package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");


    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();;
        return new VerificationPage();
    }

    public void inValidLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        errorNotification.shouldBe(visible);
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void clearFields() {
        loginField.sendKeys((Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE));
        passwordField.sendKeys((Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE));
    }
}
