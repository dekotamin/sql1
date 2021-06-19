package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;

    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("vasya", "123qwerty");}

    @Value
    public static class VerificationCode {
        private String code;

    }

    public static VerificationCode getVerificationCodeFor() {
        String codeSQL = "SELECT code FROM auth_codes INNER JOIN users ON auth_codes.user_id = users.id;";
        val runner =new QueryRunner();
        String code = "";

        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            code = runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new VerificationCode(code);}

    public static void clearCodeAuth()  throws SQLException {
        val codeSQL ="DELETE FROM auth_codes;";
        val runner =new QueryRunner();
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.update(conn, codeSQL);
        }
    }

    public static void clearDB()  throws SQLException{
        val codeSQL ="DELETE FROM users;";
        val codeSQL2 ="DELETE FROM cards;";
        val runner =new QueryRunner();
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.update(conn, codeSQL2);
            runner.update(conn, codeSQL);
        }
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }


}
