package ru.netology.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    @FindBy(css = "[data-test-id=login] input")
    private SelenideElement loginField;
    @FindBy(css = "[data-test-id=password] input")
    private SelenideElement passwordField;
    @FindBy(css = "[data-test-id=action-login]")
    private SelenideElement loginButton;

    public void enterLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
    }

    public void enterPassword(DataHelper.AuthInfo info) {
        passwordField.sendKeys(Keys.SHIFT, Keys.HOME);
        passwordField.sendKeys(Keys.DELETE);
        passwordField.setValue(info.getPassword());
    }

    public VerificationPage confirmAuth() {
        loginButton.click();
        return Selenide.page(VerificationPage.class);
    }

    public void confirmNotAuth() {
        loginButton.click();
        $(".notification_visible").shouldBe(visible);
    }

    public void checkSystemBlocked() {
        passwordField.shouldNotBe(visible);
    }
