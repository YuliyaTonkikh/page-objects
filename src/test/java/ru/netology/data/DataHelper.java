package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("user", "user1234");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }
    public static String getCardFirst() {
        return "5559 4325 2211 5599";
    }
    public static String getCardSecond() {
        return "5559 0000 8900 0902";
    }
    public static String getInvalidCard() {
        return "2211 2341 7721 9113";
    }
    @Value
    public static class Card {
        private String card;
    }
}