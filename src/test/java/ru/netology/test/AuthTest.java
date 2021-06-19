package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;
import ru.netology.page.DashboardPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    @AfterEach
    void tearDown() throws SQLException {
        DataHelper.clearCodeAuth();
    }

    @AfterAll
    static void clearDB() throws SQLException {
        DataHelper.clearDB();
    }

    @Test
    void shouldAuth() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        loginPage.enterLogin(authInfo);
        loginPage.enterPassword(authInfo);
        val verificationPage= loginPage.confirmAuth();
        val verificationCode = DataHelper.getVerificationCodeFor();
        verificationPage.validVerify(verificationCode);
        val dashboardPage = new DashboardPage();

    }

    @Test
    void shouldBlockAuth() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getWrongAuthInfo();
        loginPage.enterLogin(authInfo);
        loginPage.enterPassword(authInfo);
        loginPage.confirmNotAuth();
        loginPage.enterPassword(authInfo);
        loginPage.confirmNotAuth();
        loginPage.enterPassword(authInfo);
        loginPage.confirmNotAuth();
        loginPage.checkSystemBlocked();

    }
}