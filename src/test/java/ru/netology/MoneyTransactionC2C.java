package ru.netology;

import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransactionC2C {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferFromSecondToFirstCard() {
        val amount = 2000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getFirstCardBalance();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardSecond(), amount);
        val actual = dashboardPage.getFirstCardBalance();
        val expected = initialBalanceToCard + amount;
        val actual2 = dashboardPage.getSecondCardBalance();
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromFirstToSecondCard() {
        val amount = 3000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardFirst(), amount);
        val actualFirstCard = dashboardPage.getFirstCardBalance();
        val expectedFirstCard = initialBalanceFromCard - amount;
        val actualSecondCard = dashboardPage.getSecondCardBalance();
        val expectedSecondCard = initialBalanceToCard + amount;
        assertEquals(expectedFirstCard, actualFirstCard);
        assertEquals(expectedSecondCard, actualSecondCard);
    }

    @Test
    void shouldCheckTheTransferFromAnInvalidCard() {
        val amount = 3000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getInvalidCard(), amount);
        transferPage.invalidPayCard();
    }

    @Test
    void shouldTransferMoreLimitFromTheSecondCard() {
        val amount = 20_000;

        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardFirst(), amount);
        transferPage.validPayExtendAmount();
    }
}